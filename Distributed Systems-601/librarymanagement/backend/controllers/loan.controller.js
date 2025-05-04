import { PrismaClient } from "@prisma/client";
import { findBookById, updateBookAvailability } from "./book.controller.js";
import { findUserById } from "./user.controller.js";
const prisma = new PrismaClient();

export const loanBook = async(req,res) => {
    const {user_id, book_id, dueDate} = req.body;
    try {
        const user = await findUserById(user_id);
        if(!user) {
          return res.status(400).json({error: "User Invalid"});
        }

        const book = await findBookById(book_id);
        if(!book || book.availableCopies<1) {
            return res.status(400).json({error: "Book not available"});
        }
        
        const loan = await prisma.loan.create({
            data: {
                userId: user_id,
                bookId: book_id,
                dueDate,    
                status: 'ACTIVE',
            },
        });

        await updateBookAvailability(book_id, 'decrement');

        res.status(201).json({
          id: loan.id,
          user_id,
          book_id,
          issue_date: loan.issueDate,
          due_date: loan.dueDate,
          status: loan.status
        });
    } catch (error) {
        res.status(400).json({error: error.message});
    }
}

export const returnBook = async (req,res) => {
    const {loan_id} = req.body;
    try {
        const loan = await prisma.loan.update({
            where: {id: loan_id},
            data: {
                status: "RETURNED",
                returnDate: new Date()
            },
            include: {book: true}
        });
        await updateBookAvailability(loan.bookId, 'decrement');

        res.json({
            id: loan.id,
            user_id: loan.userId,
            book_id: loan.bookId,
            issue_date: loan.issueDate,
            due_date: loan.dueDate,
            return_date: loan.returnDate,
            status: loan.status
        });
    } catch (error) {
        res.status(400).json({error: error.message});
    }
}

export const getUserLoans = async (req,res) => {
    const user_id = parseInt(req.params.user_id);
    try {
        const loans = await prisma.loan.findMany({
            where: { userId: user_id },
            include: {
              book: { select: { id: true, title: true, author: true } }
            }
          });
        
          const formattedLoans = loans.map(loan => ({
            id: loan.id,
            book: {
              id: loan.book.id,
              title: loan.book.title,
              author: loan.book.author
            },
            issue_date: loan.issueDate?.toISOString(),
            due_date: loan.dueDate?.toISOString(),
            return_date: loan.returnDate ? loan.returnDate.toISOString() : null,
            status: loan.status
          }));
      
          res.json(formattedLoans);
    } catch (error) {
        res.status(400).json({error: error.message})
    }
}

export const getOverdueLoans = async (req, res) => {
    try {
      const currentDate = new Date();
      const loans = await prisma.loan.findMany({
        where: {
          status: 'ACTIVE',
          dueDate: { lt: currentDate }
        },
        include: {
          user: { select: { id: true, name: true, email: true } },
          book: { select: { id: true, title: true, author: true } }
        }
      });
  
      const overdueLoans = loans.map(loan => ({
        id: loan.id,
        user: {
          id: loan.user.id,
          name: loan.user.name,
          email: loan.user.email
        },
        book: {
          id: loan.book.id,
          title: loan.book.title,
          author: loan.book.author
        },
        issue_date: loan.issueDate.toISOString(),
        due_date: loan.dueDate.toISOString(),
        days_overdue: Math.floor((currentDate - loan.dueDate) / (1000 * 60 * 60 * 24))
      }));
  
      res.json(overdueLoans);
    } catch (error) {
      res.status(400).json({ error: error.message });
    }
};

export const extendLoan = async (req,res) => {
    try {
        const { extension_days } = req.body;
        const loan = await prisma.loan.findUnique({
          where: { id: parseInt(req.params.id) }
        });
    
        if (!loan || loan.status !== 'ACTIVE') {
          return res.status(400).json({ error: 'Invalid loan or already returned' });
        }
    
        const newDueDate = new Date(loan.dueDate);
        newDueDate.setDate(newDueDate.getDate() + extension_days);
    
        const updatedLoan = await prisma.loan.update({
          where: { id: parseInt(req.params.id) },
          data: {
            dueDate: newDueDate,
            extensionsCount: { increment: 1 }
          }
        });
    
        res.json({
          ...updatedLoan,
          originalDueDate: loan.dueDate,
          extendedDueDate: newDueDate
        });
      } catch (error) {
        res.status(400).json({ error: error.message });
      }
}
