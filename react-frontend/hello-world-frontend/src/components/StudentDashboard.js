import React, { useState, useEffect } from 'react';
import axios from 'axios';

const StudentDashboard = () => {
  const [exams, setExams] = useState([]);
  const [file, setFile] = useState(null);

  useEffect(() => {
    const fetchExams = async () => {
      try {
        const response = await axios.get('/api/exams/all');
        setExams(response.data);
      } catch (error) {
        console.error("Error fetching exams", error);
      }
    };
    fetchExams();
  }, []);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (examId) => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));

      const formData = new FormData();
      formData.append('file', file);
      formData.append('studentId', user.id);
      formData.append('examId', examId);

      const response = await axios.post(`/api/submissions/submit`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      if (response.status === 200) {
        alert('Submission successful');
      } else {
        alert('Failed to submit the exam');
      }
    } catch (error) {
      console.error("Error submitting exam", error);
      alert('Failed to submit the exam');
    }
  };

  const isAccessible = (startTime, endTime) => {
    const now = new Date();
    const start = new Date(startTime);
    const end = new Date(endTime);
    return now >= start && now <= end;
  };

  return (
    <div>
      <h2>Available Exams</h2>
      <ul>
        {exams.map((exam) => (
          <li key={exam.id}>
            <h3>{exam.title}</h3>
            <p>Start Time: {exam.startTime}</p>
            <p>End Time: {exam.endTime}</p>
            {exam.fileUrl && isAccessible(exam.startTime, exam.endTime) && (
              <a href={exam.fileUrl} target="_blank" rel="noopener noreferrer">View File</a>
            )}
            {!isAccessible(exam.startTime, exam.endTime) && (
              <span>Exam not accessible at this time</span>
            )}
            <input type="file" onChange={handleFileChange} />
            <button onClick={() => handleSubmit(exam.id)}>Submit Solution</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StudentDashboard;
