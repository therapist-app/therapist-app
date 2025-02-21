import { Typography } from "@mui/material";

export default function Footer() {
  return (
    <footer
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
      <Typography variant="h2">This is the footer</Typography>
    </footer>
  );
}
