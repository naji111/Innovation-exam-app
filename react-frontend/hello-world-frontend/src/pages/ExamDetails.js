import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const ExamDetails = () => {
  const { id } = useParams();
  const [exam, setExam] = useState(null);

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

  if (!exam) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>{exam.title}</h2>
      <p>Description: {exam.description}</p>
      <p>Start Time: {exam.startTime}</p>
      <p>End Time: {exam.endTime}</p>
      {exam.fileUrl && <a href={exam.fileUrl} target="_blank" rel="noopener noreferrer">Download File</a>}
    </div>
  );
};

export default ExamDetails;
