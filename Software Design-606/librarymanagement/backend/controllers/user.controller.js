import { PrismaClient } from "@prisma/client";
const prisma = new PrismaClient();

export const createUser = async (req,res) => {
    const {name,email,role} = req.body;
    try {
        const user = await prisma.user.create({data: {name,email,role}});
        res.status(201).json(user);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
}

export const getUserById = async (req,res) => {
    const id = parseInt(req.params.id);
    try {
        const user = await prisma.user.findUnique({where: {id}});
        if(!user) {
            return res.status(404).json({error: "User not found"});
        }
        res.json(user);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
}