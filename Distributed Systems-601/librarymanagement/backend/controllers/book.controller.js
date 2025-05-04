import { PrismaClient } from "@prisma/client";
const prisma = new PrismaClient();

export const addBook = async (req,res) => {
    const {title,author,isbn,copies} = req.body;
    try {
        const book = await prisma.book.create({data: {title,author,isbn,copies,availableCopies: copies}});
        res.status(201).json(book);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
}

export const getBookById = async (req,res) => {
    const id = parseInt(req.params.id);
    try {
        const book = await findBookById(id);
        if(!book) {
            return res.status(404).json({error: "User not found"});
        }
        res.json(book);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
}

export const searchBook = async (req,res) => {
    const {search} = req.query;
    try {
        const books = await prisma.book.findMany({
            where:{
                OR: [
                    {title: {contains: search}},
                    {author: {contains: search}},
                    {isbn: {contains: search}},
                ],
            },
        });
        res.json(books);
    } catch (error) {
        res.status(400).json({error: "Search failed", details: error.message});
    }
}

export const updateBook = async (req,res) => {
    const {id} = req.params;
    const {copies, availableCopies} = req.body;
    try {
        const updatedBook = await prisma.book.update({
            where: {id: Number(id)},
            data: {copies, availableCopies},
        });
        res.json(updatedBook);
    } catch (error) {
        res.status(400).json({error: "Error updating book", details: error.message});
    }
}

export const deleteBook = async(req,res) => {
    const {id} = req.params;
    try {
        const deleted = await prisma.book.delete({
            where: {id: Number(id)},
        });
        res.status(204).send();
    } catch (error) {
        res.status(400).json({error: error.message});
    }
}

export const findBookById = async (book_id) => {
    return await prisma.book.findUnique({ where: { id: book_id } });
};

export const updateBookAvailability = async (book_id, operation) => {
  
    if (!['increment', 'decrement'].includes(operation)) {
      throw new Error("Invalid operation: must be 'increment' or 'decrement'");
    }
  
    return await prisma.book.update({
      where: { id: book_id },
      data: {
        availableCopies: {
          [operation]: 1
        }
      }
    });
  };
  