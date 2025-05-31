import axios from 'axios';
import Loan from '../model/loan.model.js';
import dotenv from 'dotenv';
dotenv.config();
const USER_SERVICE_URL = process.env.USER_SERVICE_URL;
const BOOK_SERVICE_URL = process.env.BOOK_SERVICE_URL;

export const createLoan= async(data) => {
  try {
    const user = await axios.get(`${USER_SERVICE_URL}/api/users/${data.user_id}`, {
      timeout: 5000,
    });

    const book = await axios.get(`${BOOK_SERVICE_URL}/api/books/${data.book_id}`, {
      timeout: 5000,
    });
    if (book.data.availableCopies <= 0) {
      throw new Error('Book is not available');
    }

    if(!user) {
      throw new Error('User not found');
    }

    await axios.patch(`${BOOK_SERVICE_URL}/api/books/${data.book_id}/availability`,
      {
        available_copies: 1,
        operation: 'decrement',
      },
      { timeout: 5000 }
    );
    
    return Loan.create({
      userId: data.user_id,
      bookId: data.book_id,
      dueDate: new Date(data.dueDate),
      status: 'ACTIVE',
    })
  } catch (error) {
      console.error('Error creating loan:', error.message);
  }
}
export const returnBook = async (loanId) => {
  try {
    const loan = await Loan.findById(loanId);
    if (!loan) {
      throw new Error('Loan not found');
    }
    if (loan.status === 'RETURNED') {
      throw new Error('Book already returned');
    }
    await axios.patch(
      `http://${BOOK_SERVICE_URL}/api/books/${loan.bookId}/availability`,
      {
        available_copies: 1,
        operation: 'increment',
      },
      { timeout: 5000 }
    );
    const updatedLoan = await Loan.findByIdAndUpdate(
      loanId,
      {
        status: 'RETURNED',
        returnDate: new Date(),
      },
      { new: true } 
    );
    return updatedLoan;
  } catch (error) {
    if (error.response && error.response.status === 404) {
      throw new Error('Book not found');
    }
    throw error;
  }
}

export async function getUserLoans(userId) {
  try {
    const loans = await Loan.find({ userId }).sort({ issueDate: -1 });

    const enrichedLoans = await Promise.all(
      loans.map(async (loan) => {
        const [bookResponse, userResponse] = await Promise.all([
          axios.get(`${BOOK_SERVICE_URL}/api/books/${loan.bookId}`, { timeout: 5000 }),
          axios.get(`${USER_SERVICE_URL}/api/users/${loan.userId}`, { timeout: 5000 }),
        ]);

        return {
          ...loan.toObject(),
          book: {
            id: bookResponse.data.id,
            title: bookResponse.data.title,
            author: bookResponse.data.author,
          },
          user: {
            id: userResponse.data.id,
            name: userResponse.data.name,
            email: userResponse.data.email,
          },
        };
      })
    );

    return {
      loans: enrichedLoans,
      total: loans.length,
    };
  } catch (error) {
    throw error;
  }
}

export const getLoan = async (id) => {
  try {
    const loan = await Loan.findById(id);
    if (!loan) {
      return null;
    }
    const [bookResponse, userResponse] = await Promise.all([
      axios.get(`${BOOK_SERVICE_URL}/api/books/${loan.bookId}`, { timeout: 5000 }),
      axios.get(`${USER_SERVICE_URL}/api/users/${loan.userId}`, { timeout: 5000 }),
    ]);
    return {
      ...loan.toObject(),
      book: {
        id: bookResponse.data.id,
        title: bookResponse.data.title,
        author: bookResponse.data.author,
      },
      user: {
        id: userResponse.data.id,
        name: userResponse.data.name,
        email: userResponse.data.email,
      },
    };
  } catch (error) {
    console.error('Error fetching loan:', error.message);
    throw error;
  }
}
