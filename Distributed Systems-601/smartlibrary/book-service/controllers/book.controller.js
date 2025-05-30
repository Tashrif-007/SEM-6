import { StatusCodes } from 'http-status-codes';
import * as bookService from '../services/book.service.js';

export async function createBook(req, res) {
  try {
    const book = await bookService.createBook(req.body);
    res.status(StatusCodes.CREATED).json(book);
  } catch (error) {
    res.status(StatusCodes.BAD_REQUEST).json({ error: error.message });
  }
}

export async function searchBooks(req, res) {
  try {
    const { search, page = 1, per_page = 10 } = req.query;
    const result = await bookService.searchBooks(search, parseInt(page), parseInt(per_page));
    res.status(StatusCodes.OK).json(result);
  } catch (error) {
    res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({ error: error.message });
  }
}

export async function getBook(req, res) {
  try {
    const book = await bookService.getBook(req.params.id);
    if (!book) {
      return res.status(StatusCodes.NOT_FOUND).json({ error: 'Book not found' });
    }
    res.status(StatusCodes.OK).json(book);
  } catch (error) {
    res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({ error: error.message });
  }
}

export async function updateBook(req, res) {
  try {
    const book = await bookService.updateBook(req.params.id, req.body);
    if (!book) {
      return res.status(StatusCodes.NOT_FOUND).json({ error: 'Book not found' });
    }
    res.status(StatusCodes.OK).json(book);
  } catch (error) {
    res.status(StatusCodes.BAD_REQUEST).json({ error: error.message });
  }
}

export async function updateAvailability(req, res) {
  try {
    const { available_copies, operation } = req.body;
    const book = await bookService.updateAvailability(
      req.params.id,
      available_copies,
      operation
    );
    if (!book) {
      return res.status(StatusCodes.NOT_FOUND).json({ error: 'Book not found' });
    }
    res.status(StatusCodes.OK).json({
      id: book.id,
      available_copies: book.availableCopies,
      updated_at: book.updatedAt,
    });
  } catch (error) {
    res.status(StatusCodes.BAD_REQUEST).json({ error: error.message });
  }
}

export async function deleteBook(req, res) {
  try {
    const deleted = await bookService.deleteBook(req.params.id);
    if (!deleted) {
      return res.status(StatusCodes.NOT_FOUND).json({ error: 'Book not found' });
    }
    res.status(StatusCodes.NO_CONTENT).send();
  } catch (error) {
    res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({ error: error.message });
  }
}