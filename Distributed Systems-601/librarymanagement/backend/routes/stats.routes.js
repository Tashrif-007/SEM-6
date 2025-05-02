import express from 'express';
import { getActiveUsers, getOverview, getPopularBooks } from '../controllers/stats.controller.js';

const statsRouter = express.Router();

statsRouter.get("/books/popular", getPopularBooks);
statsRouter.get("/users/active", getActiveUsers);
statsRouter.get("/overview", getOverview);

export default statsRouter;