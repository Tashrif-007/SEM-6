import express from 'express';
import { addBook, deleteBook, getBookById, searchBook, updateBook } from '../controllers/book.controller.js';

const bookRouter = express.Router();

bookRouter.post("/", addBook);
bookRouter.get('/:id', getBookById);
bookRouter.get("/", searchBook);
bookRouter.put("/:id", updateBook);
bookRouter.delete("/:id", deleteBook);

export default bookRouter;