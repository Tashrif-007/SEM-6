import express from 'express';
import userRoutes from './routes/user.route.js';
import { connectDb } from './db/connectDb.js';

const app = express();

app.use(express.json());
app.use('/api/users', userRoutes);

const PORT = process.env.PORT || 8081;

app.listen(PORT, () => {
  console.log(`User Service running on port ${PORT}`);
  connectDb();
});