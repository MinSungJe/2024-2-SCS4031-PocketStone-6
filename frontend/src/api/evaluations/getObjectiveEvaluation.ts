import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';

export const getObjectiveEvaluation = async (projectId: string | number) => {
  try {
    const response = await tokenAxios.get(
      `${API_URL}/api/projects/${projectId}/evaluations/objective`,
      {
        headers: headers
      }
    );
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};