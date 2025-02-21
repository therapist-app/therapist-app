import { Button, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { TherapistOutputDTO } from "../../dto/output/TherapistOutputDTO";
import { getAllTherapists } from "../../services/therapistService";

const Dashboard = () => {
  const [therapists, setTherapists] = useState<TherapistOutputDTO[]>([]);

  useEffect(() => {
    const fetchTherapists = async () => {
      try {
        const response = await getAllTherapists();
        setTherapists(response);
      } catch (e) {
        console.error(`Error fetching therapists: ${e}`);
      }
    };

    fetchTherapists();
  }, []);

  return (
    <div>
      <Typography variant="h3">This is the Home/Dashboard page</Typography>
      <img height={200} src="/uzh-logo.jpg" alt="UZH Logo" />
      <Typography variant="body1">Example image</Typography>
      <Button onClick={() => console.log("Button clicked :)")}>Example MUI Button</Button>
      <Typography variant="body1">Fetched Therapists from Backend (should be 3):</Typography>
      <ul>
        {therapists.map((t) => {
          return (
            <li>
              {" "}
              <Typography sx={{ fontWeight: "bold" }} variant="body2">
                email: {t.email}
              </Typography>
              <Typography variant="body2">id: {t.id}</Typography>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default Dashboard;
