import React, { useState } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

const AuthComponent = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('STUDENT');
  const history = useHistory();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isLogin) {
      try {
        const response = await axios.post('/api/auth/signin', { username, password });
        // Assuming JWT token is stored in localStorage
        localStorage.setItem('token', response.data.token);
        // Redirect to dashboard
        history.push('/dashboard');
      } catch (error) {
        console.error("Error logging in", error);
      }
    } else {
      try {
        const response = await axios.post('/api/auth/signup', { username, password, role });
        // Redirect to login after successful registration
        setIsLogin(true);
      } catch (error) {
        console.error("Error registering", error);
      }
    }
  };

  return (
    <div>
      <h2>{isLogin ? "Login" : "Register"}</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Username:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        {!isLogin && (
          <div>
            <label>Role:</label>
            <select value={role} onChange={(e) => setRole(e.target.value)}>
              <option value="STUDENT">Student</option>
              <option value="TEACHER">Teacher</option>
            </select>
          </div>
        )}
        <button type="submit">{isLogin ? "Login" : "Register"}</button>
      </form>
      <button onClick={() => setIsLogin(!isLogin)}>
        {isLogin ? "Switch to Register" : "Switch to Login"}
      </button>
    </div>
  );
};

export default AuthComponent;
