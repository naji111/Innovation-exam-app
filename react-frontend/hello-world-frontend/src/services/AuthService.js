import axios from 'axios';

const AuthService = {
  getCurrentUser: async () => {
    const user = JSON.parse(localStorage.getItem('user'));
    console.log('Current user from localStorage:', user); // Debug log
    if (!user || !user.id) {
      throw new Error("No user or user ID found");
    }
    const response = await axios.get(`/api/users/${user.id}`);
    return response.data;
  }
};

export default AuthService;
