/**
 * Utility functions for file operations
 */

/**
 * Generates a file path with the project name and feature options
 * @param projectName Name of the project
 * @param path Path inside the project
 * @returns Full path including project name
 */
export const generatePath = (projectName: string, path: string): string => {
  return `${projectName}/${path}`;
};

/**
 * Gets the file extension based on the file name
 * @param fileName Name of the file
 * @returns The file extension
 */
export const getFileExtension = (fileName: string): string => {
  return fileName.slice(fileName.lastIndexOf('.') + 1);
};

/**
 * Checks if a file is a component file based on its extension
 * @param fileName Name of the file
 * @returns Whether the file is a component file
 */
export const isComponentFile = (fileName: string): boolean => {
  const extension = getFileExtension(fileName);
  return ['tsx', 'jsx'].includes(extension);
};

/**
 * Formats a file size in bytes to a human-readable format
 * @param bytes Size in bytes
 * @returns Formatted size string (e.g., "1.5 KB")
 */
export const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 Bytes';
  
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  
  return parseFloat((bytes / Math.pow(k, i)).toFixed(1)) + ' ' + sizes[i];
};