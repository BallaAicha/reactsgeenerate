import { ProjectConfig } from '../types';

/**
 * Service pour gérer la génération de projets
 * Cette implémentation se connecte réellement au backend
 */
export const ProjectGeneratorService = {
  /**
   * Génère un projet basé sur la configuration
   * @param config Configuration du projet
   * @returns Une promesse qui contient le résultat de la génération
   */
  generateProject: async (config: ProjectConfig): Promise<{success: boolean, message: string}> => {
    try {
      const response = await fetch('http://localhost:8080/api/projects/generate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(config)
      });

      if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
      }

      return {
        success: true,
        message: 'Project generated successfully'
      };
    } catch (error) {
      console.error('Error generating project:', error);
      return {
        success: false,
        message: 'Failed to generate project'
      };
    }
  },

  /**
   * Télécharge directement le projet
   * @param config Configuration du projet
   */
  downloadProject: async (config: ProjectConfig): Promise<Blob> => {
    const response = await fetch('http://localhost:8080/api/projects/generate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(config)
    });

    if (!response.ok) {
      throw new Error(`Error: ${response.status}`);
    }

    return await response.blob();
  }
};