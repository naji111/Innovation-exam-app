import React, { useState, useEffect } from 'react';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080'; // Replace with your backend URL if different

const TeacherDashboard = () => {
  const [exams, setExams] = useState([]);
  const [selectedExamId, setSelectedExamId] = useState(null);
  const [submissions, setSubmissions] = useState([]);
  const [title, setTitle] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [description, setDescription] = useState('');
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState('');

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

  const handleCreateExam = async (e) => {
    e.preventDefault();
    try {
      const user = JSON.parse(localStorage.getItem('user'));

      let fileUrl = '';
      if (file) {
        const formData = new FormData();
        formData.append('file', file);
        const uploadResponse = await axios.post('/api/files/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        fileUrl = uploadResponse.data.fileUrl;
      }

      const response = await axios.post('/api/exams/create', {
        title,
        startTime,
        endTime,
        description,
        fileUrl,
        teacher: { id: user.id }
      });
      setMessage('Exam created successfully!');
      setExams([...exams, response.data]);
    } catch (error) {
      console.error("Error creating exam", error);
      setMessage('Failed to create exam.');
    }
  };

  const handleDeleteExam = async (examId) => {
    try {
      await axios.delete(`/api/exams/${examId}`);
      setExams(exams.filter(exam => exam.id !== examId));
      setMessage('Exam and associated submissions deleted successfully');
    } catch (error) {
      console.error("Error deleting exam", error);
      setMessage('Failed to delete exam.');
    }
  };

  const handleExamSelect = async (examId) => {
    setSelectedExamId(examId);
    try {
      const response = await axios.get(`/api/submissions/exam/${examId}`);
      setSubmissions(response.data);
    } catch (error) {
      console.error("Error fetching submissions", error);
      setSubmissions([]);
    }
  };

  return (
    <div>
      <h2>Teacher Dashboard</h2>
      <form onSubmit={handleCreateExam}>
        <div>
          <label>Title:</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Start Time:</label>
          <input
            type="datetime-local"
            value={startTime}
            onChange={(e) => setStartTime(e.target.value)}
            required
          />
        </div>
        <div>
          <label>End Time:</label>
          <input
            type="datetime-local"
            value={endTime}
            onChange={(e) => setEndTime(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Description:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>
        <div>
          <label>File:</label>
          <input
            type="file"
            onChange={(e) => setFile(e.target.files[0])}
          />
        </div>
        <button type="submit">Create Exam</button>
      </form>
      {message && <p>{message}</p>}

      <h3>Existing Exams</h3>
      <ul>
        {exams.map((exam) => (
          <li key={exam.id}>
            <h4>{exam.title}</h4>
            <p>Description: {exam.description}</p>
            <p>Start Time: {exam.startTime}</p>
            <p>End Time: {exam.endTime}</p>
            {exam.fileUrl && <a href={exam.fileUrl} download>Download File</a>}
            <button onClick={() => handleExamSelect(exam.id)}>View Submissions</button>
            <button onClick={() => handleDeleteExam(exam.id)}>Delete Exam</button>
          </li>
        ))}
      </ul>

      {selectedExamId && (
        <>
          <h3>Submissions for Exam {selectedExamId}</h3>
          <ul>
            {submissions.map((submission) => (
              <li key={submission.id}>
                <p>Student: {submission.student.username}</p>
                <p>Submitted At: {submission.submittedAt}</p>
                {submission.late && <p style={{ color: 'red' }}>Late</p>}
                <a href={`http://localhost:8080/api/files/download/${submission.filePath}`} target="_blank" rel="noopener noreferrer">Download File</a>
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
};

export default TeacherDashboard;
