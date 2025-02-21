import { Typography } from "@mui/material";
import { useParams } from "react-router-dom";

const PatientChatBotEdit = () => {
  const { patientId, chatBotId } = useParams();
  return (
    <div>
      <Typography variant="h3">
        This page is editing bot with ID: "{chatBotId}" for patient with ID: "{patientId}"
      </Typography>
    </div>
  );
};

export default PatientChatBotEdit;
