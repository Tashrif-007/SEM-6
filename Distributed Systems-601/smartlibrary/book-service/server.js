import express from 'express';
import bookRoutes from './routes/book.route.js';
import {connectDb} from './db/connectDb.js';
const app = express();

app.use(express.json());
app.use('/api/books', bookRoutes);

const PORT = process.env.PORT || 8082;
app.listen(PORT, () => {
  console.log(`Book Service running on port ${PORT}`);
  connectDb();
});