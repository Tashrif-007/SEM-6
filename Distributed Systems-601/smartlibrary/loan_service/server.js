import express from 'express';
import loanRoutes from './routes/loanRoutes.js';
import { connectDb } from './db/connectDb.js';
const app = express();

app.use(express.json());
app.use('/api/loans', loanRoutes);

const PORT = process.env.PORT || 8083;
app.listen(PORT, () => {
  console.log(`Loan Service running on port ${PORT}`);
  connectDb();
});