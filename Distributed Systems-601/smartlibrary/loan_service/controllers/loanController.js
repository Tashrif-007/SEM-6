import { StatusCodes } from 'http-status-codes';
import * as loanService from '../services/loanService.js';

export async function createLoan(req, res) {
  try {
    const loan = await loanService.createLoan(req.body);
    res.status(201).json(loan);
  } catch (error) {
      return res.status(500).json({ error: error.message });
  }
}

export async function returnBook(req, res) {
  try {
    const loan = await loanService.returnBook(req.body.loan_id);
    res.status(StatusCodes.OK).json(loan);
  } catch (error) {
    if (error.message.includes('not found')) {
      return res.status(StatusCodes.BAD_REQUEST).json({ error: error.message });
    }
    res.status(StatusCodes.SERVICE_UNAVAILABLE).json({ error: 'Service unavailable' });
  }
}

export async function getUserLoans(req, res) {
  try {
    const loans = await loanService.getUserLoans(req.params.userId);
    res.status(StatusCodes.OK).json(loans);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
}

export async function getLoan(req, res) {
  try {
    const loan = await loanService.getLoan(req.params.id);
    if (!loan) {
      return res.status(StatusCodes.NOT_FOUND).json({ error: 'Loan not found' });
    }
    res.status(StatusCodes.OK).json(loan);
  } catch (error) {
    res.status(StatusCodes.SERVICE_UNAVAILABLE).json({ error: 'Service unavailable' });
  }
}