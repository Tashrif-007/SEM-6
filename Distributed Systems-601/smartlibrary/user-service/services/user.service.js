import User from '../model/user.model.js';


export const createUser = async (data) => {
  return User.create({
    name: data.name,
    email: data.email,
    role: data.role || 'student',
  })
}

export const getUser = async (id) => {
  return User.findOne({
    _id: id,
  })
}

export const updateUser = async (id, data) => {
  return User.findByIdAndUpdate(id, {
    name: data.name,
    email: data.email,
  }, {
    new: true,
  })
}
