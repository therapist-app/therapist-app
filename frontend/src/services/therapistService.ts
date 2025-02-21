import api from "../utils/api";
import { TherapistOutputDTO } from "../dto/output/TherapistOutputDTO";
import { TherapistInputDTO } from "../dto/input/TherapistInputDTO";

export const getAllTherapists = async (): Promise<TherapistOutputDTO[]> => {
  return api.get("/therapists");
};

export const createTherapist = async (therapist: TherapistInputDTO): Promise<TherapistOutputDTO> => {
  return api.post("/therapists", therapist);
};
