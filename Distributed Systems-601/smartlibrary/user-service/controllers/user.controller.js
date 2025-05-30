import { StatusCodes } from 'http-status-codes';
import * as userService from '../services/user.service.js';

export const createUser = async(req, res) => {
  try {
    const user = await userService.createUser(req.body);
    res.status(StatusCodes.CREATED).json(user);
  } catch (error) {
    res.status(StatusCodes.BAD_REQUEST).json({ error: error.message });
  }
}

export const getUser = async(req, res) => {
  try {
    const user = await userService.getUser(req.params.id);
    if (!user) {
      return res.status(StatusCodes.NOT_FOUND).json({ error: 'User not found' });
    }
    res.status(StatusCodes.OK).json(user);
  } catch (error) {
    res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({ error: error.message });
  }
}

export const updateUser = async(req, res) => {
  try {
    const user = await userService.updateUser(req.params.id, req.body);
    if (!user) {
      return res.status(StatusCodes.NOT_FOUND).json({ error: 'User not found' });
    }
    res.status(StatusCodes.OK).json(user);
  } catch (error) {
    res.status(StatusCodes.BAD_REQUEST).json({ error: error.message });
  }
}