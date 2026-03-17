import { useCallback, useState } from "react";
import { useDropzone } from "react-dropzone";
import api from "../api/axios";

export default function ResumeUpload({ onParsed }) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const onDrop = useCallback(
    async (acceptedFiles) => {
      const file = acceptedFiles[0];
      if (!file) return;

      // Only accept PDF files
      if (file.type !== "application/pdf") {
        setError("Please upload a PDF file.");
        return;
      }

      setLoading(true);
      setError(null);

      const formData = new FormData();
      formData.append("file", file);

      try {
        const response = await api.post("/resume/upload", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });
        onParsed(response.data);
      } catch (err) {
        setError("Failed to parse resume. Please try again.");
        console.error(err);
      } finally {
        setLoading(false);
      }
    },
    [onParsed],
  );

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: { "application/pdf": [".pdf"] },
    multiple: false,
  });

  return (
    <div className="w-full max-w-xl mx-auto">
      <div
        {...getRootProps()}
        className={`border-2 border-dashed rounded-xl p-10 text-center cursor-pointer transition-colors
          ${isDragActive ? "border-blue-500 bg-blue-50" : "border-gray-300 hover:border-blue-400 hover:bg-gray-50"}`}
      >
        <input {...getInputProps()} />
        {loading ? (
          <p className="text-blue-500 font-medium">Parsing resume...</p>
        ) : isDragActive ? (
          <p className="text-blue-500 font-medium">Drop the PDF here</p>
        ) : (
          <div>
            <p className="text-gray-600 font-medium">
              Drag and drop your resume PDF here
            </p>
            <p className="text-gray-400 text-sm mt-1">or click to browse</p>
          </div>
        )}
      </div>

      {error && (
        <p className="mt-3 text-red-500 text-sm text-center">{error}</p>
      )}
    </div>
  );
}
