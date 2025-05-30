import { Router } from 'express';
import * as bookController from '../controllers/book.controller.js';

const router = Router();

router.post('/', bookController.createBook);
router.get('/', bookController.searchBooks);
router.get('/:id', bookController.getBook);
router.put('/:id', bookController.updateBook);
router.patch('/:id/availability', bookController.updateAvailability);
router.delete('/:id', bookController.deleteBook);

export default router;