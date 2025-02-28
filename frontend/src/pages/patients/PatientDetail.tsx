import { Typography } from "@mui/material";
import { useParams } from "react-router-dom";

const PatientDetail = () => {
  const { patientId } = useParams();
  return (
    <div>
      <Typography variant="h3">
        This is the patient detail page of patient with ID: "{patientId}"
      </Typography>
    </div>
  );
};

export default PatientDetail;
