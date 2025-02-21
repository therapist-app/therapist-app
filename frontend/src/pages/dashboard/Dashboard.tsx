import { Button, Typography } from "@mui/material";

const Dashboard = () => {
  return (
    <div>
      <Typography variant="h3">This is the Home/Dashboard page</Typography>
      <img height={200} src="/uzh-logo.jpg" alt="UZH Logo" />
      <Typography variant="body1">Example image</Typography>
      <Button onClick={() => console.log("Button clicked :)")}>Example MUI Button</Button>
    </div>
  );
};

export default Dashboard;
