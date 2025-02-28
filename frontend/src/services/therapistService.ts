import api from "../utils/api";
import { TherapistOutputDTO } from "../dto/output/TherapistOutputDTO";
import { CreateTherapistDTO } from "../dto/input/TherapistInputDTO";

export const getAllTherapists = async (): Promise<TherapistOutputDTO[]> => {
  const response = await api.get("/therapists");
  return response.data;
};

export const createTherapist = async (
  therapist: CreateTherapistDTO,
): Promise<TherapistOutputDTO> => {
  const response = await api.post("/therapists", therapist);
  return response.data;
};
