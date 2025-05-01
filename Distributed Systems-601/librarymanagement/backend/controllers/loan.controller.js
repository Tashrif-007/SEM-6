import { PrismaClient } from "@prisma/client";
const prisma = new PrismaClient();

export const loanBook = async(req,res) => {
    const {user_id, book_id, dueDate} = req.body;
    try {
        const book = await prisma.book.findUnique({where: {id: book_id}});
        if(!book || book.availableCopies<1) {
            return res.status(400).json({error: "Book not available"});
        }
        
        const loan = await prisma.loan.create({
            data: {
                userId: user_id,
                bookId: book_id,
                dueDate,    
                status: 'ACTIVE',
            },
        });

        await prisma.book.update({
            where: {id: book_id},
            data: {
                availableCopies: {
                    decrement: 1
                },
            },
        });
        res.status(201).json(loan);
    } catch (error) {
        res.status(400).json({error: error.message});
    }
}