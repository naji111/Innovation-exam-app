import axios from 'axios';

const UserService = {
  register: async (userData) => {
    const response = await axios.post('/api/auth/signup', userData);
    return response.data;
  },

  login: async (userData) => {
    const response = await axios.post('/api/auth/signin', userData);
    return response.data;
  },

  getCurrentUser: async () => {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error("No token found");
    }
    const response = await axios.get('/api/auth/user', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  }
};

export default UserService;
