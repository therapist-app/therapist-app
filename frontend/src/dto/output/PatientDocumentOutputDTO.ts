export interface PatientDocumentOutputDTO {
  id: string
  fileName: string
  fileType: string
  fileData: ArrayBuffer | Blob
}
