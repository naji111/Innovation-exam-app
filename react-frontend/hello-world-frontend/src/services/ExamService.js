import axios from 'axios';

const ExamService = {
  getAllExams: async () => {
    const response = await axios.get('/api/exams/all');
    return response.data;
  },

  getExamById: async (id) => {
    const response = await axios.get(`/api/exams/${id}`);
    return response.data;
  },

  createExam: async (examData) => {
    const response = await axios.post('/api/exams/create', examData);
    return response.data;
  }
};

export default ExamService;
