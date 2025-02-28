import { Typography } from '@mui/material';
import { useTranslation } from 'react-i18next';

const NotFound = () => {
    const { t } = useTranslation();

    return (
        <div>
            <Typography variant='h3'>{t('not_found.title')}</Typography>
            <Typography variant='body1'>{t('not_found.message')}</Typography>
        </div>
    );
};

export default NotFound;
