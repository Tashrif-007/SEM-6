import { Router } from 'express';
import {createUser, getUser, updateUser} from '../controllers/user.controller.js';

const router = Router();

router.post('/', createUser);
router.get('/:id', getUser);
router.put('/:id', updateUser);

export default router;