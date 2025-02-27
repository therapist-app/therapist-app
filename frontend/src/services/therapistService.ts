import api from "../utils/api";
import { TherapistOutputDTO } from "../dto/output/TherapistOutputDTO";
import { CreateTherapistDTO } from "../dto/input/TherapistInputDTO";

export const getAllTherapists = async (): Promise<TherapistOutputDTO[]> => {
  return api.get("/therapists");
};

export const createTherapist = async (therapist: CreateTherapistDTO): Promise<TherapistOutputDTO> => {
  return api.post("/therapists", therapist);
};
