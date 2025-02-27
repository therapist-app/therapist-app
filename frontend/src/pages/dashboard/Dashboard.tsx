import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  Button,
  Box,
  CardActionArea,
  IconButton,
  Typography,
  Card,
  CardContent,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
  Snackbar,
  Alert,
} from "@mui/material";
import CardActions from "@mui/material/CardActions";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import AddIcon from "@mui/icons-material/Add";
import MoreVertIcon from "@mui/icons-material/MoreVert";

import { TbMessageChatbot } from "react-icons/tb";
import { RiRobot2Line } from "react-icons/ri";
import { IoPersonOutline, IoBulbOutline } from "react-icons/io5";
import { PiBookOpenTextLight } from "react-icons/pi";

import Layout from "../../generalComponents/Layout";
import { getPatientsForTherapist, registerPatient } from "../../services/patientService";
import api from "../../utils/api";
import { handleError } from "../../utils/handleError";
import { PatientOutputDTO } from "../../dto/output/PatientOutputDTO";
import { getAllChatbotTemplatesForTherapist } from "../../services/chatbotTemplateService";

interface DashboardProps {
  workspaceId?: string | null;
}

const Dashboard: React.FC<DashboardProps> = ({ workspaceId: propWorkspaceId }) => {
  const navigate = useNavigate();

  const [patients, setPatients] = useState<PatientOutputDTO[]>([]);
  const [openPatientDialog, setOpenPatientDialog] = useState(false);
  const [newPatientName, setNewPatientName] = useState("");
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState<"info" | "success" | "error" | "warning">("info");
  const [chatbots, setChatbots] = useState<any[]>([]);
  const initialWorkspaceId = propWorkspaceId ?? sessionStorage.getItem("workspaceId") ?? "";
  const [workspaceId, setWorkspaceId] = useState(initialWorkspaceId);
  const [openBotDialog, setOpenBotDialog] = useState(false);
  const [chatbotName, setChatbotName] = useState("");
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [currentChatbot, setCurrentChatbot] = useState<any>(null);
  const [openRenameDialog, setOpenRenameDialog] = useState(false);

  const fetchTherapistPatients = async () => {
    try {
      const therapistId = sessionStorage.getItem("therapistId");
      if (!therapistId) {
        throw new Error("No therapistId found in session storage.");
      }
      const response = await getPatientsForTherapist();
      setPatients(response);
    } catch (error: any) {
      const errorMessage = handleError(error);
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
    }
  };

  const handleOpenPatientDialog = () => {
    setOpenPatientDialog(true);
  };

  const handleClosePatientDialog = () => {
    setOpenPatientDialog(false);
    setNewPatientName("");
  };

  const handleCreatePatient = async () => {
    try {
      const therapistId = sessionStorage.getItem("therapistId");
      if (!therapistId) {
        throw new Error("No therapistId found in session storage.");
      }
      await registerPatient(therapistId, { name: newPatientName });
      await fetchTherapistPatients();
      setSnackbarMessage("Patient registered successfully!");
      setSnackbarSeverity("success");
      setSnackbarOpen(true);
      handleClosePatientDialog();
    } catch (error: any) {
      const errorMessage = handleError(error);
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
    }
  };

  useEffect(() => {
    fetchTherapistPatients();
    setWorkspaceId(sessionStorage.getItem("workspaceId") ?? "");
  }, []);

  useEffect(() => {
    const fetchChatbotTemplates = async () => {
      try {
        const therapistId = sessionStorage.getItem("therapistId");
        if (!therapistId) return;

        const response = await getAllChatbotTemplatesForTherapist(therapistId);
        setChatbots(response);
      } catch (error: any) {
        const errorMessage = handleError(error);
        setSnackbarMessage(errorMessage);
        setSnackbarSeverity("error");
        setSnackbarOpen(true);
      }
    };

    fetchChatbotTemplates();
  }, []);

  const handleCloseSnackbar = (_event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === "clickaway") return;
    setSnackbarOpen(false);
  };

  const handlePatientClick = (patientId: string) => {
    navigate(`/patients/${patientId}`);
  };

  const handleOpenBotDialog = () => {
    setOpenBotDialog(true);
  };

  const handleCloseBotDialog = () => {
    setOpenBotDialog(false);
    setChatbotName("");
  };

  const handleCreateChatbot = async () => {
    try {
      const therapistId = sessionStorage.getItem("therapistId");
      if (!therapistId || !workspaceId) {
        throw new Error("Missing required IDs");
      }

      const chatbotConfigurations = {
        chatbotName,
        chatbotModel: "gpt-3.5-turbo",
        chatbotIcon: "Chatbot",
        chatbotLanguage: "English",
        chatbotRole: "Possibility Engine",
        chatbotTone: "friendly",
        welcomeMessage: "Hello! How can I assist you today?",
        workspaceId,
      };

      const response = await api.post(`/api/therapists/${therapistId}/chatbot-templates`, chatbotConfigurations);

      setChatbots((prev) => [...prev, response]);
      setSnackbarMessage("Chatbot created successfully!");
      setSnackbarSeverity("success");
      setSnackbarOpen(true);
      handleCloseBotDialog();
    } catch (error: any) {
      const errorMessage = handleError(error);
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
    }
  };

  const handleChatbotClick = async (chatbotId: string) => {
    try {
      console.log("chatbotId", chatbotId);
      sessionStorage.setItem("chatbotId", chatbotId);
      navigate(`/?workspace_id=${workspaceId}/?chatbot_template_id=${chatbotId}`);
    } catch (error: any) {
      const errorMessage = handleError(error);
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
    }
  };

  const handleMenuClick = (event: React.MouseEvent<HTMLButtonElement>, chatbot: any) => {
    setAnchorEl(event.currentTarget);
    setCurrentChatbot(chatbot);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleRename = () => {
    if (currentChatbot) {
      setChatbotName(currentChatbot.chatbotName);
      setOpenRenameDialog(true);
    }
    handleMenuClose();
  };

  const handleRenameChatbot = async () => {
    try {
      if (!currentChatbot) return;
      const therapistId = sessionStorage.getItem("therapistId");
      if (!therapistId) throw new Error("No therapist found");

      const response = await api.put(`/api/therapists/${therapistId}/chatbot-templates/${currentChatbot.id}`, {
        chatbotName,
      });

      const updatedChatbots = chatbots.map((bot) => (bot.id === currentChatbot.id ? response : bot));
      setChatbots(updatedChatbots);

      setSnackbarMessage("Chatbot renamed successfully");
      setSnackbarSeverity("success");
      setSnackbarOpen(true);
      setOpenRenameDialog(false);
    } catch (error: any) {
      const errorMessage = handleError(error);
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
    }
  };

  const handleClone = async () => {
    if (!currentChatbot) return;
    try {
      const therapistId = sessionStorage.getItem("therapistId");
      if (!therapistId) throw new Error("No therapist found");

      const response = await api.post(`/api/therapists/${therapistId}/chatbot-templates/${currentChatbot.id}/clone`);

      setChatbots((prev) => [...prev, response]);
      setSnackbarMessage("Chatbot cloned successfully");
      setSnackbarSeverity("success");
      setSnackbarOpen(true);
      handleMenuClose();
    } catch (error: any) {
      const errorMessage = handleError(error);
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
    }
  };

  const handleDelete = async () => {
    try {
      if (!currentChatbot) return;
      const therapistId = sessionStorage.getItem("therapistId");
      if (!therapistId) throw new Error("No therapist found");

      await api.delete(`/api/therapists/${therapistId}/chatbot-templates/${currentChatbot.id}`);

      const updatedChatbots = chatbots.filter((bot) => bot.id !== currentChatbot.id);
      setChatbots(updatedChatbots);

      setSnackbarMessage("Chatbot deleted successfully");
      setSnackbarSeverity("success");
      setSnackbarOpen(true);
      handleMenuClose();
    } catch (error: any) {
      const errorMessage = handleError(error);
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
    }
  };

  const commonButtonStyles = {
    borderRadius: 20,
    textTransform: "none",
    fontSize: "1rem",
    minWidth: "130px",
    maxWidth: "130px",
    padding: "6px 24px",
    lineHeight: 1.75,
    backgroundColor: "#635BFF",
    backgroundImage: "linear-gradient(45deg, #635BFF 30%, #7C4DFF 90%)",
    boxShadow: "0 3px 5px 2px rgba(99, 91, 255, .3)",
    color: "white",
    "&:hover": {
      backgroundColor: "#7C4DFF",
    },
    margin: 1,
  };

  const disabledButtonStyles = {
    ...commonButtonStyles,
    backgroundImage: "lightgrey",
    "&:hover": {
      disabled: "true",
    },
  };

  const cancelButtonStyles = {
    borderRadius: 20,
    textTransform: "none",
    fontSize: "1rem",
    minWidth: "130px",
    maxWidth: "130px",
    padding: "6px 24px",
    lineHeight: 1.75,
    backgroundColor: "white",
    color: "#635BFF",
    "&:hover": {
      backgroundColor: "#f0f0f0",
    },
    margin: 1,
  };

  const dialogStyle = {
    width: "500px",
    height: "300px",
  };

  const getIconComponent = (iconName: string) => {
    switch (iconName) {
      case "Chatbot":
        return <TbMessageChatbot />;
      case "Robot":
        return <RiRobot2Line />;
      case "Person":
        return <IoPersonOutline />;
      case "Bulb":
        return <IoBulbOutline />;
      case "Book":
        return <PiBookOpenTextLight />;
      default:
        return null;
    }
  };

  // const templates = [
  //   {
  //     id: "1",
  //     chatbotName: "FAQ Bot",
  //     description: "Answers frequently asked questions about lectures, courses, or projects.",
  //     chatbotModel: "gpt-3.5-turbo",
  //     chatbotIcon: "Robot",
  //     chatbotLanguage: "English",
  //     chatbotRole: "Possibility Engine",
  //     chatbotTone: "friendly",
  //     welcomeMessage: "Hello! How can I assist you today?",
  //   },
  //   {
  //     id: "2",
  //     chatbotName: "Study Bot",
  //     description: "Helps groups to research and solve problems together.",
  //     chatbotModel: "gpt-3.5-turbo",
  //     chatbotIcon: "Chatbot",
  //     chatbotLanguage: "English",
  //     chatbotRole: "Collaboration Coach",
  //     chatbotTone: "professional",
  //     welcomeMessage: "Welcome! Let’s solve problems together.",
  //   },
  //   {
  //     id: "3",
  //     chatbotName: "Storytelling Bot",
  //     description: "Provides stories that include diverse views, abilities and experiences.",
  //     chatbotModel: "gpt-3.5-turbo",
  //     chatbotIcon: "Book",
  //     chatbotLanguage: "English",
  //     chatbotRole: "Storyteller",
  //     chatbotTone: "friendly",
  //     welcomeMessage: "Hello! Let me tell you a story.",
  //   },
  // ];

  // const handleTemplateSelect = async (template: any) => {
  //   try {
  //     const therapistId = sessionStorage.getItem("therapistId");
  //     if (!therapistId || !workspaceId) {
  //       throw new Error("Missing required IDs");
  //     }

  //     const { chatbotName, configuration } = template;
  //     const chatbotConfigurations = {
  //       chatbotName,
  //       ...configuration,
  //       workspaceId,
  //     };

  //     const response = await api.post(`/api/therapists/${therapistId}/chatbot-templates`, chatbotConfigurations);

  //     setChatbots((prev) => [...prev, response]);
  //     setSnackbarMessage("Template applied successfully!");
  //     setSnackbarSeverity("success");
  //     setSnackbarOpen(true);
  //     handleCloseBotDialog();
  //   } catch (error: any) {
  //     const errorMessage = handleError(error);
  //     setSnackbarMessage(errorMessage);
  //     setSnackbarSeverity("error");
  //     setSnackbarOpen(true);
  //   }
  // };

  return (
    <Layout>
      <Box sx={{ marginBottom: 4 }}>
        <Card
          sx={{
            p: 2,
            boxShadow: "none",
            border: "1px solid #e0e0e0",
            borderRadius: "8px",
          }}
        >
          <Typography variant="h6" gutterBottom>
            Register a new patient
          </Typography>
          <Button
            variant="contained"
            startIcon={<AddIcon />}
            onClick={handleOpenPatientDialog}
            sx={commonButtonStyles}
            style={{ minWidth: "200px", maxWidth: "200px" }}
          >
            Add Patient
          </Button>
        </Card>
      </Box>

      <Typography variant="h5" sx={{ marginBottom: 3 }}>
        Patients
      </Typography>
      {patients.length > 0 ? (
        <Box
          sx={{
            display: "flex",
            flexWrap: "wrap",
            gap: 2,
            justifyContent: "flex-start",
          }}
        >
          {patients.map((patient) => (
            <Card
              key={patient.id}
              variant="outlined"
              sx={{
                mb: 2,
                maxWidth: "300px",
                minWidth: "300px",
                maxHeight: "150px",
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                position: "relative",
                boxShadow: "none",
                border: "1px solid #e0e0e0",
                borderRadius: "8px",
              }}
            >
              <CardActionArea onClick={() => handlePatientClick(patient.id)}>
                <CardContent>
                  <Typography variant="h6">{patient.name ?? "Unnamed Patient"}</Typography>
                  <Typography variant="body2" color="textSecondary">
                    Patient ID: {patient.id}
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          ))}
        </Box>
      ) : (
        <Typography variant="subtitle1" sx={{ textAlign: "center" }}>
          No patients found.
        </Typography>
      )}

      <Dialog open={openPatientDialog} onClose={handleClosePatientDialog} PaperProps={{ sx: dialogStyle }}>
        <DialogTitle>New Patient</DialogTitle>
        <DialogContent sx={{ mt: 1 }}>
          <DialogContentText sx={{ mb: 1 }}>Enter information to register a new patient.</DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="patient-name"
            label="Patient Name"
            type="text"
            fullWidth
            variant="outlined"
            value={newPatientName}
            onChange={(e) => setNewPatientName(e.target.value)}
          />
        </DialogContent>
        <DialogActions sx={{ justifyContent: "right", pr: 2 }}>
          <Button onClick={handleClosePatientDialog} sx={cancelButtonStyles}>
            Cancel
          </Button>
          <Button
            onClick={handleCreatePatient}
            variant="contained"
            sx={newPatientName.trim() !== "" ? commonButtonStyles : disabledButtonStyles}
            disabled={newPatientName.trim() === ""}
          >
            Register
          </Button>
        </DialogActions>
      </Dialog>

      <Box sx={{ mt: 6, mb: 4 }}>
        <Card
          sx={{
            p: 2,
            boxShadow: "none",
            border: "1px solid #e0e0e0",
            borderRadius: "8px",
          }}
        >
          <Typography variant="h6" gutterBottom>
            Create new chatbot template
          </Typography>
          <Button
            variant="contained"
            startIcon={<AddIcon />}
            onClick={handleOpenBotDialog}
            sx={commonButtonStyles}
            style={{ minWidth: "200px", maxWidth: "200px" }}
          >
            Create bot
          </Button>
        </Card>
      </Box>

      <Typography variant="h5" sx={{ mt: 6, mb: 3 }}>
        Your Chatbot Templates
      </Typography>
      {chatbots.length > 0 ? (
        <Box
          sx={{
            display: "flex",
            flexWrap: "wrap",
            gap: 2,
            justifyContent: "flex-start",
          }}
        >
          {chatbots.map((bot, index) => (
            <Card
              key={bot.chatbotId || index}
              variant="outlined"
              sx={{
                mb: 2,
                maxWidth: "300px",
                minWidth: "300px",
                maxHeight: "250px",
                minHeight: "250px",
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                position: "relative",
                boxShadow: "none",
                border: "1px solid #e0e0e0",
                borderRadius: "8px",
              }}
            >
              <CardActionArea onClick={() => handleChatbotClick(bot.id)}>
                <CardContent>
                  <Typography variant="h6">{bot.chatbotName || "Unnamed Bot"}</Typography>
                  <Typography variant="body2" color="textSecondary">
                    {bot.welcomeMessage || "No welcome message set"}
                  </Typography>
                  <Typography variant="body1" sx={{ mt: 1 }}>
                    {`Language: ${bot.chatbotLanguage}`}
                  </Typography>
                  <Typography variant="body1" sx={{ mt: 1 }}>
                    {`Role: ${bot.chatbotRole}`}
                  </Typography>
                  <Typography variant="body1">{`Tone: ${bot.chatbotTone}`}</Typography>
                  <Typography variant="body1" sx={{ fontSize: "48px", textAlign: "center" }}>
                    {getIconComponent(bot.chatbotIcon)}
                  </Typography>
                </CardContent>
              </CardActionArea>

              <CardActions
                disableSpacing
                sx={{
                  position: "absolute",
                  top: 0,
                  right: 0,
                }}
              >
                <IconButton
                  aria-label="more"
                  aria-controls="chatbot-menu"
                  aria-haspopup="true"
                  onClick={(event) => handleMenuClick(event, bot)}
                >
                  <MoreVertIcon />
                </IconButton>
              </CardActions>
            </Card>
          ))}
        </Box>
      ) : (
        <Typography variant="subtitle1" sx={{ textAlign: "center" }}>
          No chatbots created yet.
        </Typography>
      )}

      <Menu
        id="chatbot-menu"
        anchorEl={anchorEl}
        keepMounted
        open={Boolean(anchorEl && currentChatbot)}
        onClose={handleMenuClose}
      >
        <MenuItem onClick={handleRename}>Rename</MenuItem>
        <MenuItem onClick={handleClone}>Clone</MenuItem>
        <MenuItem onClick={handleDelete}>Delete</MenuItem>
      </Menu>

      <Dialog open={openBotDialog} onClose={handleCloseBotDialog} PaperProps={{ sx: dialogStyle }}>
        <DialogTitle>New Bot</DialogTitle>
        <DialogContent sx={{ mt: 1 }}>
          <DialogContentText sx={{ mb: 1 }}>What would you like to name your bot?</DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="bot-name"
            label="Enter bot name"
            type="text"
            fullWidth
            variant="outlined"
            value={chatbotName}
            onChange={(e) => setChatbotName(e.target.value)}
          />
        </DialogContent>
        <DialogActions sx={{ justifyContent: "right", pr: 2 }}>
          <Button onClick={handleCloseBotDialog} sx={cancelButtonStyles}>
            Cancel
          </Button>
          <Button
            onClick={handleCreateChatbot}
            variant="contained"
            sx={chatbotName.trim() !== "" ? commonButtonStyles : disabledButtonStyles}
            disabled={chatbotName.trim() === ""}
          >
            Create Bot
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog open={openRenameDialog} onClose={() => setOpenRenameDialog(false)} PaperProps={{ sx: dialogStyle }}>
        <DialogTitle>Rename Bot</DialogTitle>
        <DialogContent sx={{ mt: 1 }}>
          <DialogContentText sx={{ mb: 1 }}>Enter the new name for your bot.</DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="rename"
            label="Enter new bot name"
            type="text"
            fullWidth
            variant="outlined"
            value={chatbotName}
            onChange={(e) => setChatbotName(e.target.value)}
          />
        </DialogContent>
        <DialogActions sx={{ justifyContent: "right", pr: 2 }}>
          <Button onClick={() => setOpenRenameDialog(false)} sx={cancelButtonStyles}>
            Cancel
          </Button>
          <Button
            onClick={handleRenameChatbot}
            variant="contained"
            sx={chatbotName.trim() !== "" ? commonButtonStyles : disabledButtonStyles}
            disabled={chatbotName.trim() === ""}
          >
            Save
          </Button>
        </DialogActions>
      </Dialog>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{ width: "100%" }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </Layout>
  );
};

export default Dashboard;
