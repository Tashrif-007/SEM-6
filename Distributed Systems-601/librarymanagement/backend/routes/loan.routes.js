import express from 'express';
import { loanBook } from '../controllers/loan.controller.js';

const loanRouter = express.Router();

loanRouter.post("/", loanBook);

export default loanRouter;