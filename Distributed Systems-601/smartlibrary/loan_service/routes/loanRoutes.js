import { Router } from 'express';
import * as loanController from '../controllers/loanController.js';

const router = Router();

router.post('/', loanController.createLoan);
router.post('/returns', loanController.returnBook);
router.get('/user/:userId', loanController.getUserLoans);
router.get('/:id', loanController.getLoan);

export default router;