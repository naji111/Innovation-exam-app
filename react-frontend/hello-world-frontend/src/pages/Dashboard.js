import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from '../services/AuthService';

const Dashboard = () => {
  const [role, setRole] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserRole = async () => {
      try {
        const user = await AuthService.getCurrentUser();
        setRole(user.role);
        if (user.role === 'STUDENT') {
          navigate('/student-dashboard');
        } else if (user.role === 'TEACHER') {
          navigate('/teacher-dashboard');
        }
      } catch (error) {
        console.error("Error fetching user role", error);
        navigate('/login');
      }
    };
    fetchUserRole();
  }, [navigate]);

  return (
    <div>
      <h2>Loading...</h2>
    </div>
  );
};

export default Dashboard;
