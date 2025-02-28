import { Typography } from '@mui/material';
import { Link, useLocation } from 'react-router-dom';
import { useTranslation } from "react-i18next";

export default function Header() {
    const { t } = useTranslation();
    const location = useLocation();
    const pathname = location.pathname;
    const examplePatientId = 'patient-1234';
    const exampleBotId = 'chatBot-5678';
    const exampleBotTemplateId = 'chatBotTemplate-999';

    const links = [
        { path: '/', label: t('header.dashboard') },
        { path: '/register', label: t('header.register') },
        { path: '/login', label: t('header.login') },
        { path: '/patients', label: t('header.patients') },
        {
            path: `/patients/${examplePatientId}`,
            label: t('header.patient_detail', { id: examplePatientId }),
        },
        {
            path: `/patients/${examplePatientId}/chatBot/create`,
            label: t('header.patient_create_bot'),
        },
        {
            path: `/patients/${examplePatientId}/chatBot/${exampleBotId}`,
            label: t('header.bot_edit', { id: exampleBotId }),
        },
        { path: '/chatBotTemplate/create', label: t('header.create_bot_template') },
        {
            path: `/chatBotTemplate/${exampleBotTemplateId}`,
            label: t('header.edit_bot_template', { id: exampleBotTemplateId }),
        },
        {
            path: '/settings',
            label: t('header.settings'),
        },
    ];

    return (
        <header
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
            <Typography variant='h2'>{t('header.title')}</Typography>
            <Typography variant='h4'>{t('header.subtitle')}</Typography>
            <ul
                style={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '10px',
                    marginTop: '10px',
                }}
            >
                {links.map(({ path, label }) => (
                    <li key={path}>
                        <Link
                            to={path}
                            style={{
                                padding: '5px 10px',
                                borderRadius: '5px',
                                textDecoration: 'none',
                                backgroundColor: pathname === path ? 'green' : 'transparent',
                                color: pathname === path ? 'white' : 'black',
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