import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const ExamComponent = () => {
  const { id } = useParams();
  const [exam, setExam] = useState(null);
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const fetchExam = async () => {
      try {
        const response = await axios.get(`/api/exams/${id}`);
        setExam(response.data);
      } catch (error) {
        console.error("Error fetching exam details", error);
      }
    };
    fetchExam();
  }, [id]);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('file', file);

    try {
      await axios.post(`/api/submissions/submit`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      setMessage('Submission successful!');
    } catch (error) {
      console.error("Error submitting exam", error);
      setMessage('Submission failed!');
    }
  };

  if (!exam) return <div>Loading...</div>;

  return (
    <div>
      <h2>{exam.title}</h2>
      <p>Start Time: {exam.startTime}</p>
      <p>End Time: {exam.endTime}</p>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Upload your answer (PDF):</label>
          <input type="file" onChange={handleFileChange} accept="application/pdf" required />
        </div>
        <button type="submit">Submit</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default ExamComponent;
