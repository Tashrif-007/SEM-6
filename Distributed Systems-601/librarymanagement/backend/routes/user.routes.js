import express from 'express';
import { createUser, getUserById } from '../controllers/user.controller.js';

const userRouter = express.Router();

userRouter.post("/", createUser);
userRouter.get("/:id", getUserById);

export default userRouter;