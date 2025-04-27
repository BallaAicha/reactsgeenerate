////package com.sgab.projectgenerator.service;
////
////import com.sgab.projectgenerator.model.ProjectRequest;
////import org.springframework.stereotype.Service;
////
////@Service
////public class TemplateService {
////
////    public String getPackageJsonTemplate(ProjectRequest request) {
////        return String.format("""
////            {
////              "name": "%s",
////              "private": true,
////              "version": "%s",
////              "type": "module",
////              "scripts": {
////                "dev": "vite",
////                "build": "vite build",
////                "preview": "vite preview"
////              },
////              "dependencies": {
////                "react": "^18.2.0",
////                "react-dom": "^18.2.0"%s%s%s
////              },
////              "devDependencies": {
////                "@vitejs/plugin-react": "^4.0.0",
////                "vite": "^4.3.9"%s
////              }
////            }
////            """,
////            request.getProjectName(),
////            request.getVersion(),
////            request.getFeatures().isRouting() ? ",\n    \"react-router-dom\": \"^6.11.2\"" : "",
////            request.getFeatures().isI18n() ? ",\n    \"i18next\": \"^22.5.0\",\n    \"react-i18next\": \"^12.3.1\"" : "",
////            request.getFeatures().isApiClient() ? ",\n    \"axios\": \"^1.4.0\"" : "",
////            request.getLanguage().equals("typescript") ? ",\n    \"typescript\": \"^5.0.2\",\n    \"@types/react\": \"^18.2.8\",\n    \"@types/react-dom\": \"^18.2.4\"" : ""
////        );
////    }
////
////    public String getViteConfigTemplate(ProjectRequest request) {
////        if (request.getLanguage().equals("typescript")) {
////            return """
////                import { defineConfig } from 'vite';
////                import react from '@vitejs/plugin-react';
////
////                export default defineConfig({
////                  plugins: [react()],
////                  server: {
////                    port: 3000
////                  }
////                });
////                """;
////        } else {
////            return """
////                import { defineConfig } from 'vite';
////                import react from '@vitejs/plugin-react';
////
////                export default defineConfig({
////                  plugins: [react()],
////                  server: {
////                    port: 3000
////                  }
////                });
////                """;
////        }
////    }
////
////    public String getIndexHtmlTemplate(ProjectRequest request) {
////        return String.format("""
////            <!DOCTYPE html>
////            <html lang="en">
////              <head>
////                <meta charset="UTF-8" />
////                <link rel="icon" type="image/svg+xml" href="/vite.svg" />
////                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
////                <title>%s</title>
////              </head>
////              <body>
////                <div id="root"></div>
////                <script type="module" src="/src/main.%s"></script>
////              </body>
////            </html>
////            """,
////            request.getProjectName(),
////            request.getLanguage().equals("typescript") ? "tsx" : "jsx"
////        );
////    }
////
////    public String getMainTemplate(ProjectRequest request) {
////        String ext = request.getLanguage().equals("typescript") ? "tsx" : "jsx";
////        return String.format("""
////            import React from 'react';
////            import ReactDOM from 'react-dom/client';
////            import App from './App.%s';
////            import './index.css';
////
////            ReactDOM.createRoot(document.getElementById('root')%s).render(
////              <React.StrictMode>
////                <App />
////              </React.StrictMode>
////            );
////            """,
////            ext,
////            request.getLanguage().equals("typescript") ? " as HTMLElement" : ""
////        );
////    }
////
////    public String getAppTemplate(ProjectRequest request) {
////        return """
////            import React from 'react';
////
////            function App() {
////              return (
////                <div className="App">
////                  <h1>Welcome to Your React App</h1>
////                </div>
////              );
////            }
////
////            export default App;
////            """;
////    }
////
////    public String getIndexCssTemplate() {
////        return """
////            :root {
////              font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
////              line-height: 1.5;
////              font-weight: 400;
////            }
////
////            body {
////              margin: 0;
////              min-width: 320px;
////              min-height: 100vh;
////            }
////            """;
////    }
////
////    public String getApiClientTemplate(ProjectRequest request) {
////        String ext = request.getLanguage().equals("typescript") ? ".ts" : ".js";
////        return """
////            import axios from 'axios';
////
////            const apiClient = axios.create({
////              baseURL: import.meta.env.VITE_API_URL,
////              headers: {
////                'Content-Type': 'application/json'
////              }
////            });
////
////            export default apiClient;
////            """;
////    }
////
////    public String getEnglishLanguageTemplate() {
////        return """
////            {
////              "common": {
////                "welcome": "Welcome",
////                "loading": "Loading...",
////                "error": "An error occurred"
////              },
////              "auth": {
////                "login": "Login",
////                "logout": "Logout",
////                "email": "Email",
////                "password": "Password"
////              }
////            }
////            """;
////    }
////
////    public String getFrenchLanguageTemplate() {
////        return """
////            {
////              "common": {
////                "welcome": "Bienvenue",
////                "loading": "Chargement...",
////                "error": "Une erreur est survenue"
////              },
////              "auth": {
////                "login": "Connexion",
////                "logout": "Déconnexion",
////                "email": "Email",
////                "password": "Mot de passe"
////              }
////            }
////            """;
////    }
////
////    public String getTenantConfigTemplate(ProjectRequest request) {
////        String ext = request.getLanguage().equals("typescript") ? ".ts" : ".js";
////        return """
////            export const tenantConfig = {
////              defaultTenant: 'default',
////              tenants: {
////                default: {
////                  name: 'Default Tenant',
////                  theme: {
////                    primary: '#2563eb',
////                    secondary: '#374151'
////                  }
////                }
////              }
////            };
////            """;
////    }
////
////    public String getReadmeTemplate(ProjectRequest request) {
////        return String.format("""
////            # %s
////
////            %s
////
////            ## Getting Started
////
////            1. Install dependencies:
////               ```bash
////               npm install
////               ```
////
////            2. Start the development server:
////               ```bash
////               npm run dev
////               ```
////
////            3. Build for production:
////               ```bash
////               npm run build
////               ```
////
////            ## Features
////
////            - %s
////            - Built with Vite
////            %s
////            %s
////            %s
////
////            ## Project Structure
////
////            ```
////            src/
////            ├── assets/
////            │   ├── images/
////            │   └── css/
////            ├── components/
////            ├── config/
////            │   └── environment/
////            ├── pages/
////            ├── routes/
////            └── packages/
////                ├── core/
////                └── feat-login/
////            ```
////            """,
////            request.getProjectName(),
////            request.getDescription(),
////            request.getLanguage().equals("typescript") ? "TypeScript support" : "JavaScript",
////            request.getFeatures().isRouting() ? "- React Router for navigation" : "",
////            request.getFeatures().isI18n() ? "- Internationalization support" : "",
////            request.getFeatures().isApiClient() ? "- Axios for API communication" : ""
////        );
////    }
////}
//
//package com.sgab.projectgenerator.service;
//
//import com.sgab.projectgenerator.model.ProjectRequest;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TemplateService {
//
//    public String getPackageJsonTemplate(ProjectRequest request) {
//        return String.format("""
//            {
//              "name": "%s",
//              "private": true,
//              "version": "%s",
//              "type": "module",
//              "scripts": {
//                "dev": "vite",
//                "build": "vite build",
//                "preview": "vite preview"
//              },
//              "dependencies": {
//                "react": "^18.2.0",
//                "react-dom": "^18.2.0"%s%s%s
//              },
//              "devDependencies": {
//                "@vitejs/plugin-react": "^4.0.0",
//                "vite": "^4.3.9"%s
//              }
//            }
//            """,
//                request.getProjectName(),
//                request.getVersion(),
//                request.getFeatures().isRouting() ? ",\n    \"react-router-dom\": \"^6.11.2\"" : "",
//                request.getFeatures().isI18n() ? ",\n    \"i18next\": \"^22.5.0\",\n    \"react-i18next\": \"^12.3.1\"" : "",
//                request.getFeatures().isApiClient() ? ",\n    \"axios\": \"^1.4.0\"" : "",
//                request.getLanguage().equals("typescript") ? ",\n    \"typescript\": \"^5.0.2\",\n    \"@types/react\": \"^18.2.8\",\n    \"@types/react-dom\": \"^18.2.4\"" : ""
//        );
//    }
//
//    public String getViteConfigTemplate(ProjectRequest request) {
//        if (request.getLanguage().equals("typescript")) {
//            return """
//                import { defineConfig } from 'vite';
//                import react from '@vitejs/plugin-react';
//
//                export default defineConfig({
//                  plugins: [react()],
//                  server: {
//                    port: 3000
//                  }
//                });
//                """;
//        } else {
//            return """
//                import { defineConfig } from 'vite';
//                import react from '@vitejs/plugin-react';
//
//                export default defineConfig({
//                  plugins: [react()],
//                  server: {
//                    port: 3000
//                  }
//                });
//                """;
//        }
//    }
//
//    public String getIndexHtmlTemplate(ProjectRequest request) {
//        return String.format("""
//            <!DOCTYPE html>
//            <html lang="en">
//              <head>
//                <meta charset="UTF-8" />
//                <link rel="icon" type="image/svg+xml" href="/vite.svg" />
//                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
//                <title>%s</title>
//              </head>
//              <body>
//                <div id="root"></div>
//                <script type="module" src="/src/main.%s"></script>
//              </body>
//            </html>
//            """,
//                request.getProjectName(),
//                request.getLanguage().equals("typescript") ? "tsx" : "jsx"
//        );
//    }
//
//    public String getMainTemplate(ProjectRequest request) {
//        String ext = request.getLanguage().equals("typescript") ? "tsx" : "jsx";
//        return String.format("""
//            import React from 'react';
//            import ReactDOM from 'react-dom/client';
//            import App from './App.%s';
//            import './index.css';
//
//            ReactDOM.createRoot(document.getElementById('root')%s).render(
//              <React.StrictMode>
//                <App />
//              </React.StrictMode>
//            );
//            """,
//                ext,
//                request.getLanguage().equals("typescript") ? " as HTMLElement" : ""
//        );
//    }
//
//    public String getAppTemplate(ProjectRequest request) {
//        return """
//            import React from 'react';
//
//            function App() {
//              return (
//                <div className="App">
//                  <h1>Welcome to Your React App</h1>
//                </div>
//              );
//            }
//
//            export default App;
//            """;
//    }
//
//    public String getIndexCssTemplate() {
//        return """
//            :root {
//              font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
//              line-height: 1.5;
//              font-weight: 400;
//            }
//
//            body {
//              margin: 0;
//              min-width: 320px;
//              min-height: 100vh;
//            }
//            """;
//    }
//
//    public String getApiClientTemplate(ProjectRequest request) {
//        String ext = request.getLanguage().equals("typescript") ? ".ts" : ".js";
//        return """
//            import axios from 'axios';
//
//            const apiClient = axios.create({
//              baseURL: import.meta.env.VITE_API_URL,
//              headers: {
//                'Content-Type': 'application/json'
//              }
//            });
//
//            export default apiClient;
//            """;
//    }
//
//    public String getEnglishLanguageTemplate() {
//        return """
//            {
//              "common": {
//                "welcome": "Welcome",
//                "loading": "Loading...",
//                "error": "An error occurred"
//              },
//              "auth": {
//                "login": "Login",
//                "logout": "Logout",
//                "email": "Email",
//                "password": "Password"
//              }
//            }
//            """;
//    }
//
//    public String getFrenchLanguageTemplate() {
//        return """
//            {
//              "common": {
//                "welcome": "Bienvenue",
//                "loading": "Chargement...",
//                "error": "Une erreur est survenue"
//              },
//              "auth": {
//                "login": "Connexion",
//                "logout": "Déconnexion",
//                "email": "Email",
//                "password": "Mot de passe"
//              }
//            }
//            """;
//    }
//
//    public String getTenantConfigTemplate(ProjectRequest request) {
//        String ext = request.getLanguage().equals("typescript") ? "ts" : "js";
//        return String.format("""
//            export const tenantConfig = {
//              defaultTenant: 'default',
//              tenants: {
//                default: {
//                  name: 'Default Tenant',
//                  theme: {
//                    primary: '#2563eb',
//                    secondary: '#374151'
//                  },
//                  apiUrl: 'https://api.default-tenant.com'
//                },
//                tenant1: {
//                  name: 'Tenant 1',
//                  theme: {
//                    primary: '#10b981',
//                    secondary: '#1f2937'
//                  },
//                  apiUrl: 'https://api.tenant1.com'
//                }
//              }
//            };
//            """);
//    }
//
//    public String getButtonComponentTemplate(ProjectRequest request) {
//        String ext = request.getLanguage().equals("typescript") ? "tsx" : "jsx";
//        String typeScript = request.getLanguage().equals("typescript") ?
//                "import React, { ButtonHTMLAttributes } from 'react';\n\ninterface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {\n  variant?: 'primary' | 'secondary' | 'outline';\n  size?: 'sm' | 'md' | 'lg';\n}\n\n" :
//                "import React from 'react';\n\n";
//
//        return typeScript + """
//            const Button = ({
//              children,
//              variant = 'primary',
//              size = 'md',
//              className = '',
//              ...props
//            }) => {
//              const baseStyles = 'inline-flex items-center justify-center rounded-md font-medium transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 disabled:opacity-50';
//
//              const variants = {
//                primary: 'bg-primary text-white hover:bg-primary/90',
//                secondary: 'bg-secondary text-white hover:bg-secondary/90',
//                outline: 'border border-input bg-transparent hover:bg-accent hover:text-accent-foreground'
//              };
//
//              const sizes = {
//                sm: 'h-8 px-3 text-xs',
//                md: 'h-10 px-4 py-2',
//                lg: 'h-12 px-6 py-3 text-lg'
//              };
//
//              const classes = `${baseStyles} ${variants[variant]} ${sizes[size]} ${className}`;
//
//              return (
//                <button className={classes} {...props}>
//                  {children}
//                </button>
//              );
//            };
//
//            export default Button;
//            """;
//    }
//
//    public String getLayoutComponentTemplate(ProjectRequest request) {
//        String ext = request.getLanguage().equals("typescript") ? "tsx" : "jsx";
//        String typeScript = request.getLanguage().equals("typescript") ?
//                "import React, { ReactNode } from 'react';\n\ninterface LayoutProps {\n  children: ReactNode;\n  header?: ReactNode;\n  footer?: ReactNode;\n  sidebar?: ReactNode;\n}\n\n" :
//                "import React from 'react';\n\n";
//
//        return typeScript + """
//            const Layout = ({ children, header, footer, sidebar }) => {
//              return (
//                <div className="min-h-screen flex flex-col">
//                  {header && (
//                    <header className="sticky top-0 z-40 border-b bg-background">
//                      {header}
//                    </header>
//                  )}
//
//                  <div className="flex-1 flex">
//                    {sidebar && (
//                      <aside className="w-64 border-r bg-background hidden md:block">
//                        {sidebar}
//                      </aside>
//                    )}
//
//                    <main className="flex-1 p-4">
//                      {children}
//                    </main>
//                  </div>
//
//                  {footer && (
//                    <footer className="border-t bg-background">
//                      {footer}
//                    </footer>
//                  )}
//                </div>
//              );
//            };
//
//            export default Layout;
//            """;
//    }
//
//    public String getReadmeTemplate(ProjectRequest request) {
//        return String.format("""
//            # %s
//
//            %s
//
//            ## Getting Started
//
//            1. Install dependencies:
//               ```bash
//               npm install
//               ```
//
//            2. Start the development server:
//               ```bash
//               npm run dev
//               ```
//
//            3. Build for production:
//               ```bash
//               npm run build
//               ```
//
//            ## Features
//
//            - %s
//            - Built with Vite
//            %s
//            %s
//            %s
//
//            ## Project Structure
//
//            ```
//            src/
//            ├── assets/
//            │   ├── images/
//            │   └── css/
//            ├── components/
//            │   ├── button.tsx
//            │   └── layout.tsx
//            ├── config/
//            │   ├── environment/
//            │   └── tenantConfig/
//            │       └── tenantConfig.ts
//            ├── pages/
//            ├── routes/
//            └── packages/
//                └── core/
//                    └── feat-login/
//                        ├── components/
//                        ├── core/
//                        └── web/
//            ```
//            """,
//                request.getProjectName(),
//                request.getDescription(),
//                request.getLanguage().equals("typescript") ? "TypeScript support" : "JavaScript",
//                request.getFeatures().isRouting() ? "- React Router for navigation" : "",
//                request.getFeatures().isI18n() ? "- Internationalization support" : "",
//                request.getFeatures().isApiClient() ? "- Axios for API communication" : ""
//        );
//    }
//}


package com.sgab.projectgenerator.service;

import com.sgab.projectgenerator.model.ProjectRequest;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    public String getPackageJsonTemplate(ProjectRequest request) {
        return String.format("""
            {
              "name": "%s",
              "private": true,
              "version": "%s",
              "type": "module",
              "scripts": {
                "dev": "vite",
                "build": "vite build",
                "preview": "vite preview"
              },
              "dependencies": {
                "react": "^18.2.0",
                "react-dom": "^18.2.0"%s%s%s
              },
              "devDependencies": {
                "@vitejs/plugin-react": "^4.0.0",
                "vite": "^4.3.9"%s
              }
            }
            """,
                request.getProjectName(),
                request.getVersion(),
                request.getFeatures().isRouting() ? ",\n    \"react-router-dom\": \"^6.11.2\"" : "",
                request.getFeatures().isI18n() ? ",\n    \"i18next\": \"^22.5.0\",\n    \"react-i18next\": \"^12.3.1\"" : "",
                request.getFeatures().isApiClient() ? ",\n    \"axios\": \"^1.4.0\"" : "",
                request.getLanguage().equals("typescript") ? ",\n    \"typescript\": \"^5.0.2\",\n    \"@types/react\": \"^18.2.8\",\n    \"@types/react-dom\": \"^18.2.4\"" : ""
        );
    }

    public String getViteConfigTemplate(ProjectRequest request) {
        if (request.getLanguage().equals("typescript")) {
            return """
                import { defineConfig } from 'vite';
                import react from '@vitejs/plugin-react';

                export default defineConfig({
                  plugins: [react()],
                  server: {
                    port: 3000
                  }
                });
                """;
        } else {
            return """
                import { defineConfig } from 'vite';
                import react from '@vitejs/plugin-react';

                export default defineConfig({
                  plugins: [react()],
                  server: {
                    port: 3000
                  }
                });
                """;
        }
    }

    public String getIndexHtmlTemplate(ProjectRequest request) {
        return String.format("""
            <!DOCTYPE html>
            <html lang="en">
              <head>
                <meta charset="UTF-8" />
                <link rel="icon" type="image/svg+xml" href="/vite.svg" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <title>%s</title>
              </head>
              <body>
                <div id="root"></div>
                <script type="module" src="/src/main.%s"></script>
              </body>
            </html>
            """,
                request.getProjectName(),
                request.getLanguage().equals("typescript") ? "tsx" : "jsx"
        );
    }

    public String getMainTemplate(ProjectRequest request) {
        String ext = request.getLanguage().equals("typescript") ? "tsx" : "jsx";
        return String.format("""
            import React from 'react';
            import ReactDOM from 'react-dom/client';
            import App from './App.%s';
            import './index.css';
            
            ReactDOM.createRoot(document.getElementById('root')%s).render(
              <React.StrictMode>
                <App />
              </React.StrictMode>
            );
            """,
                ext,
                request.getLanguage().equals("typescript") ? " as HTMLElement" : ""
        );
    }

    public String getAppTemplate(ProjectRequest request) {
        return """
            import React from 'react';
            
            function App() {
              return (
                <div className="App">
                  <h1>Welcome to Your React App</h1>
                </div>
              );
            }
            
            export default App;
            """;
    }

    public String getButtonComponentTemplate(ProjectRequest request) {
        if (request.getLanguage().equals("typescript")) {
            return """
                import React from 'react';
                
                interface ButtonProps {
                  children: React.ReactNode;
                  variant?: 'primary' | 'secondary' | 'outline';
                  onClick?: () => void;
                  disabled?: boolean;
                }
                
                const Button: React.FC<ButtonProps> = ({
                  children,
                  variant = 'primary',
                  onClick,
                  disabled = false
                }) => {
                  const getButtonClasses = () => {
                    const baseClasses = 'px-4 py-2 rounded font-medium focus:outline-none transition-colors';
                    
                    switch (variant) {
                      case 'primary':
                        return `${baseClasses} bg-blue-600 text-white hover:bg-blue-700`;
                      case 'secondary':
                        return `${baseClasses} bg-gray-600 text-white hover:bg-gray-700`;
                      case 'outline':
                        return `${baseClasses} border border-blue-600 text-blue-600 hover:bg-blue-50`;
                      default:
                        return baseClasses;
                    }
                  };
                
                  return (
                    <button
                      className={getButtonClasses()}
                      onClick={onClick}
                      disabled={disabled}
                    >
                      {children}
                    </button>
                  );
                };
                
                export default Button;
                """;
        } else {
            return """
                import React from 'react';
                
                const Button = ({
                  children,
                  variant = 'primary',
                  onClick,
                  disabled = false
                }) => {
                  const getButtonClasses = () => {
                    const baseClasses = 'px-4 py-2 rounded font-medium focus:outline-none transition-colors';
                    
                    switch (variant) {
                      case 'primary':
                        return `${baseClasses} bg-blue-600 text-white hover:bg-blue-700`;
                      case 'secondary':
                        return `${baseClasses} bg-gray-600 text-white hover:bg-gray-700`;
                      case 'outline':
                        return `${baseClasses} border border-blue-600 text-blue-600 hover:bg-blue-50`;
                      default:
                        return baseClasses;
                    }
                  };
                
                  return (
                    <button
                      className={getButtonClasses()}
                      onClick={onClick}
                      disabled={disabled}
                    >
                      {children}
                    </button>
                  );
                };
                
                export default Button;
                """;
        }
    }

    public String getLayoutComponentTemplate(ProjectRequest request) {
        if (request.getLanguage().equals("typescript")) {
            return """
                import React from 'react';
                
                interface LayoutProps {
                  children: React.ReactNode;
                  header?: React.ReactNode;
                  footer?: React.ReactNode;
                }
                
                const Layout: React.FC<LayoutProps> = ({ children, header, footer }) => {
                  return (
                    <div className="min-h-screen flex flex-col">
                      {header && (
                        <header className="bg-white shadow-sm">
                          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                            {header}
                          </div>
                        </header>
                      )}
                
                      <main className="flex-grow">
                        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
                          {children}
                        </div>
                      </main>
                
                      {footer && (
                        <footer className="bg-gray-50">
                          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                            {footer}
                          </div>
                        </footer>
                      )}
                    </div>
                  );
                };
                
                export default Layout;
                """;
        } else {
            return """
                import React from 'react';
                
                const Layout = ({ children, header, footer }) => {
                  return (
                    <div className="min-h-screen flex flex-col">
                      {header && (
                        <header className="bg-white shadow-sm">
                          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                            {header}
                          </div>
                        </header>
                      )}
                
                      <main className="flex-grow">
                        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
                          {children}
                        </div>
                      </main>
                
                      {footer && (
                        <footer className="bg-gray-50">
                          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                            {footer}
                          </div>
                        </footer>
                      )}
                    </div>
                  );
                };
                
                export default Layout;
                """;
        }
    }

    public String getIndexCssTemplate() {
        return """
            :root {
              font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
              line-height: 1.5;
              font-weight: 400;
            }
            
            body {
              margin: 0;
              min-width: 320px;
              min-height: 100vh;
            }
            """;
    }

    public String getApiClientTemplate(ProjectRequest request) {
        String ext = request.getLanguage().equals("typescript") ? ".ts" : ".js";
        return """
            import axios from 'axios';
            
            const apiClient = axios.create({
              baseURL: import.meta.env.VITE_API_URL,
              headers: {
                'Content-Type': 'application/json'
              }
            });
            
            export default apiClient;
            """;
    }

    public String getEnglishLanguageTemplate() {
        return """
            {
              "common": {
                "welcome": "Welcome",
                "loading": "Loading...",
                "error": "An error occurred"
              },
              "auth": {
                "login": "Login",
                "logout": "Logout",
                "email": "Email",
                "password": "Password"
              }
            }
            """;
    }

    public String getFrenchLanguageTemplate() {
        return """
            {
              "common": {
                "welcome": "Bienvenue",
                "loading": "Chargement...",
                "error": "Une erreur est survenue"
              },
              "auth": {
                "login": "Connexion",
                "logout": "Déconnexion",
                "email": "Email",
                "password": "Mot de passe"
              }
            }
            """;
    }

    public String getTenantConfigTemplate(ProjectRequest request) {
        if (request.getLanguage().equals("typescript")) {
            return """
                interface TenantTheme {
                  primary: string;
                  secondary: string;
                  accent?: string;
                }
                
                interface Tenant {
                  name: string;
                  theme: TenantTheme;
                  logo?: string;
                  features?: string[];
                }
                
                interface TenantConfiguration {
                  defaultTenant: string;
                  tenants: Record<string, Tenant>;
                }
                
                export const tenantConfig: TenantConfiguration = {
                  defaultTenant: 'default',
                  tenants: {
                    default: {
                      name: 'Default Tenant',
                      theme: {
                        primary: '#2563eb',
                        secondary: '#374151'
                      }
                    }
                  }
                };
                """;
        } else {
            return """
                export const tenantConfig = {
                  defaultTenant: 'default',
                  tenants: {
                    default: {
                      name: 'Default Tenant',
                      theme: {
                        primary: '#2563eb',
                        secondary: '#374151'
                      }
                    }
                  }
                };
                """;
        }
    }

    public String getReadmeTemplate(ProjectRequest request) {
        return String.format("""
            # %s
            
            %s
            
            ## Getting Started
            
            1. Install dependencies:
               ```bash
               npm install
               ```
            
            2. Start the development server:
               ```bash
               npm run dev
               ```
            
            3. Build for production:
               ```bash
               npm run build
               ```
            
            ## Features
            
            - %s
            - Built with Vite
            %s
            %s
            %s
            
            ## Project Structure
            
            ```
            src/
            ├── assets/
            │   ├── images/
            │   └── css/
            ├── pages/
            │   └── components/
            │       ├── button.tsx
            │       └── layout.tsx
            ├── config/
            │   ├── environment/
            │   └── tenantConfig/
            │       └── tenantConfig.ts
            ├── routes/
            └── packages/
                └── core/
                    └── feat-login/
                        ├── components/
                        ├── core/
                        └── web/
            ```
            """,
                request.getProjectName(),
                request.getDescription(),
                request.getLanguage().equals("typescript") ? "TypeScript support" : "JavaScript",
                request.getFeatures().isRouting() ? "- React Router for navigation" : "",
                request.getFeatures().isI18n() ? "- Internationalization support" : "",
                request.getFeatures().isApiClient() ? "- Axios for API communication" : ""
        );
    }

    public String getNavigationTemplate(ProjectRequest request) {
        return """
            import { useNavigate } from 'react-router-dom';

            const useNavigation = () => {
              const navigate = useNavigate();

              return {
                goToHome: () => navigate('/'),
                goToLogin: () => navigate('/login'),
              };
            };

            export default useNavigation;
            """;
    }

    public String getRoutesTemplate(ProjectRequest request) {
        return """
            import React from 'react';
            import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
            import App from '../App';

            const AppRoutes = () => (
              <Router>
                <Routes>
                  <Route path="/" element={<App />} />
                </Routes>
              </Router>
            );

            export default AppRoutes;
            """;
    }

    public String getEnvTemplate(ProjectRequest request) {
        return """
            export const environment = {
              apiUrl: import.meta.env.VITE_API_URL,
              environmentName: 'development'
            };
            """;
    }

    public String getUtilsTemplate(ProjectRequest request) {
        return """
            export const formatDate = (date) => {
              return new Date(date).toLocaleDateString();
            };

            export const isEmpty = (value) => {
              return value == null || value.length === 0;
            };
            """;
    }
}