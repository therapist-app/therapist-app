import { useState } from "react";
import { TextField, Button, Typography, Container, Box } from "@mui/material";
import { TherapistInputDTO } from "../../dto/input/TherapistInputDTO";
import { createTherapist } from "../../services/therapistService";

const Register = () => {
  const [formData, setFormData] = useState<TherapistInputDTO>({
    email: "",
    password: "",
  });
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<boolean>(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setSuccess(false);

    if (!formData.email || !formData.password) {
      setError("Both fields are required.");
      return;
    }

    try {
      await createTherapist(formData);
      setSuccess(true);
      setFormData({ email: "", password: "" });
    } catch (err) {
      setError("Failed to register therapist. Please try again.");
      console.error("Registration error:", err);
    }
  };

  return (
    <Container maxWidth="xs">
      <Box sx={{ textAlign: "center", mt: 4 }}>
        <Typography variant="h4" gutterBottom>
          Register Therapist
        </Typography>
      </Box>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Email"
          name="email"
          type="email"
          value={formData.email}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />
        <TextField
          label="Password"
          name="password"
          type="password"
          value={formData.password}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />
        {error && <Typography color="error">{error}</Typography>}
        {success && <Typography color="primary">Therapist registered successfully!</Typography>}
        <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
          Register
        </Button>
      </form>
    </Container>
  );
};

export default Register;
