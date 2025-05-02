import {PrismaClient} from '@prisma/client';
const prisma = new PrismaClient();

export const getPopularBooks = async (req,res) => {
    try {
        const books = await prisma.loan.groupBy({
            by: ['bookId'],
            _count: {bookId: true},
            orderBy: {_count: {bookId: 'desc'}},
            take: 3
        });
        const detailedBooks = await Promise.all(
            books.map(async ({bookId, _count}) => {
                const book = await prisma.book.findUnique({
                    where: {id: bookId},
                    select: {id: true, title: true, author: true}
                });
                return {
                    bookId,
                    ...book,
                    borrowCount: _count.bookId,
                }
            })
        )
        res.json(detailedBooks);
    } catch (error) {
        res.status(400).json({error: error.message})
    }
}

export const getActiveUsers = async (req, res) => {
    try {
      const users = await prisma.loan.groupBy({
        by: ['userId'],
        _count: { userId: true },
        orderBy: { _count: { userId: 'desc' } },
        take: 2
      });
  
      const detailedUsers = await Promise.all(
        users.map(async ({ userId, _count }) => {
          const user = await prisma.user.findUnique({
            where: { id: userId },
            select: { id: true, name: true }
          });
          const currentBorrows = await prisma.loan.count({
            where: { userId, status: 'ACTIVE' }
          });
          return {
            userId,
            ...user,
            booksBorrowed: _count.userId,
            currentBorrows
          };
        })
      );
  
      res.json(detailedUsers);
    } catch (error) {
      res.status(400).json({ error: error.message });
    }
};

export const getOverview = async (req, res) => {
    try {
      const [totalBooks, totalUsers, activeLoans, overdueLoans, todayLoans, todayReturns] = await Promise.all([
        prisma.book.count(),
        prisma.user.count(),
        prisma.loan.count({ where: { status: 'ACTIVE' } }),
        prisma.loan.count({
          where: {
            status: 'ACTIVE',
            dueDate: { lt: new Date() }
          }
        }),
        prisma.loan.count({
          where: {
            issueDate: { gte: new Date(new Date().setHours(0, 0, 0, 0)) }
          }
        }),
        prisma.loan.count({
          where: {
            returnDate: { gte: new Date(new Date().setHours(0, 0, 0, 0)) }
          }
        })
      ]);
  
      const booksAvailable = await prisma.book.aggregate({
        _sum: { availableCopies: true }
      });
  
      res.json({
        totalBooks,
        totalUsers,
        booksAvailable: booksAvailable._sum.availableCopies,
        booksBorrowed: activeLoans,
        overdueLoans,
        loansToday: todayLoans,
        returnsToday: todayReturns
      });
    } catch (error) {
      res.status(400).json({ error: error.message });
    }
  };
