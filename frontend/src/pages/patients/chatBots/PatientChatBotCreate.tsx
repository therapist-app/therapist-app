import { Typography } from "@mui/material";
import { useParams } from "react-router-dom";

const PatientChatBotCreate = () => {
  const { patientId } = useParams();
  return (
    <div>
      <Typography variant="h3">This page is for creating a new bot for patient with ID: "{patientId}"</Typography>
    </div>
  );
};

export default PatientChatBotCreate;
