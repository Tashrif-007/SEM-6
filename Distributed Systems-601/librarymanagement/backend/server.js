import express from 'express';
import dotenv from 'dotenv';
import cors from 'cors'
import userRouter from './routes/user.routes.js';
import bookRouter from './routes/book.routes.js';
import loanRouter from './routes/loan.routes.js';
import statsRouter from './routes/stats.routes.js';

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3500;

app.use(cors({
    origin: '*',
}))
app.use(express.json());

app.use("/api/users", userRouter);
app.use("/api/books", bookRouter);
app.use("/api/loans", loanRouter);
app.use("/api/stats", statsRouter);

app.listen(PORT, () => {
    console.log(`Server running on ${PORT}`);
})