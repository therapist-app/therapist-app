import { Typography } from '@mui/material';
import { useTranslation } from 'react-i18next';

export default function Footer() {
    const { t } = useTranslation();

    return (
        <footer
            style={{
                width: '100%',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                paddingTop: '20px',
                paddingBottom: '20px',
                backgroundColor: '#D3D3D3',
            }}
        >
            <Typography variant='h2'>{t('footer.title')}</Typography>
        </footer>
    );
}
