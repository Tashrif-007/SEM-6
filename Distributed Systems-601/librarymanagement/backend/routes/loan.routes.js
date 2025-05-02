import express from 'express';
import { extendLoan, getOverdueLoans, getUserLoans, loanBook, returnBook } from '../controllers/loan.controller.js';

const loanRouter = express.Router();

loanRouter.post("/", loanBook);
loanRouter.post("/returns", returnBook);
loanRouter.get("/overdue", getOverdueLoans);
loanRouter.post("/:id/extend", extendLoan);
loanRouter.get("/:user_id", getUserLoans);

export default loanRouter;