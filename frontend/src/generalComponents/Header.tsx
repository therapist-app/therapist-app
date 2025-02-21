import { Typography } from "@mui/material";
import { Link, useLocation } from "react-router-dom";

export default function Header() {
  const location = useLocation();
  const pathname = location.pathname;
  const examplePatientId = "patient-1234";
  const exampleBotId = "chatBot-5678";
  const exampleBotTemplateId = "chatBotTemplate-999";

  const links = [
    { path: "/", label: "Dashboard Page" },
    { path: "/register", label: "Register Page" },
    { path: "/login", label: "Login Page" },
    { path: "/patients", label: "Patient Overview Page" },
    { path: `/patients/${examplePatientId}`, label: `Example Patient Detail Page for ID: "${examplePatientId}"` },
    { path: `/patients/${examplePatientId}/chatBot/create`, label: "Example Patient Create Bot Page" },
    {
      path: `/patients/${examplePatientId}/chatBot/${exampleBotId}`,
      label: `Example Bot Edit Page for Bot ID: "${exampleBotId}"`,
    },
    { path: "/chatBotTemplate/create", label: "Create ChatBot Template Page" },
    {
      path: `/chatBotTemplate/${exampleBotTemplateId}`,
      label: `Edit ChatBot Template for ID: "${exampleBotTemplateId}"`,
    },
  ];

  return (
    <header
      style={{
        width: "100%",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        paddingTop: "20px",
        paddingBottom: "20px",
        backgroundColor: "#D3D3D3",
      }}
    >
      <Typography variant="h2">This is the header</Typography>
      <Typography variant="h4">These are the current pages:</Typography>
      <ul style={{ display: "flex", flexDirection: "column", gap: "10px", marginTop: "10px" }}>
        {links.map(({ path, label }) => (
          <li key={path}>
            <Link
              to={path}
              style={{
                padding: "5px 10px",
                borderRadius: "5px",
                textDecoration: "none",
                backgroundColor: pathname === path ? "green" : "transparent",
                color: pathname === path ? "white" : "black",
              }}
            >
              {label}
            </Link>
          </li>
        ))}
      </ul>
    </header>
  );
}
