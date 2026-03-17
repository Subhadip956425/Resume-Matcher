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

      if (file.type !== "application/pdf") {
        setError("Only PDF files are supported.");
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
        className={`relative border-2 border-dashed rounded-2xl p-14 text-center cursor-pointer transition-all duration-300
          ${
            isDragActive
              ? "border-blue-500 bg-blue-50 scale-[1.01]"
              : "border-slate-200 bg-white hover:border-blue-400 hover:bg-blue-50/50 hover:shadow-md"
          }`}
      >
        <input {...getInputProps()} />

        <div
          className={`w-14 h-14 rounded-2xl mx-auto mb-4 flex items-center justify-center transition-colors
          ${isDragActive ? "bg-blue-100" : "bg-slate-100"}`}
        >
          {loading ? (
            <svg
              className="w-6 h-6 text-blue-500 animate-spin"
              fill="none"
              viewBox="0 0 24 24"
            >
              <circle
                className="opacity-25"
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                strokeWidth="4"
              />
              <path
                className="opacity-75"
                fill="currentColor"
                d="M4 12a8 8 0 018-8v8z"
              />
            </svg>
          ) : (
            <svg
              className={`w-6 h-6 ${isDragActive ? "text-blue-500" : "text-slate-400"}`}
              fill="none"
              stroke="currentColor"
              strokeWidth="1.5"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M3 16.5v2.25A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75V16.5m-13.5-9L12 3m0 0l4.5 4.5M12 3v13.5"
              />
            </svg>
          )}
        </div>

        {loading ? (
          <div>
            <p className="text-blue-600 font-semibold text-sm">
              Parsing your resume...
            </p>
            <p className="text-slate-400 text-xs mt-1">
              Extracting skills, experience and salary
            </p>
          </div>
        ) : isDragActive ? (
          <p className="text-blue-600 font-semibold text-sm">
            Release to upload
          </p>
        ) : (
          <div>
            <p className="text-slate-700 font-semibold text-sm">
              Drag and drop your resume here
            </p>
            <p className="text-slate-400 text-xs mt-1">
              or click to browse — PDF only
            </p>
          </div>
        )}
      </div>

      {error && (
        <div className="mt-3 flex items-center gap-2 bg-red-50 border border-red-200 rounded-lg px-4 py-2.5">
          <svg
            className="w-4 h-4 text-red-500 shrink-0"
            fill="currentColor"
            viewBox="0 0 20 20"
          >
            <path
              fillRule="evenodd"
              d="M10 18a8 8 0 100-16 8 8 0 000 16zm-.75-4.75a.75.75 0 001.5 0v-4.5a.75.75 0 00-1.5 0v4.5zm.75-7.5a.75.75 0 100 1.5.75.75 0 000-1.5z"
              clipRule="evenodd"
            />
          </svg>
          <p className="text-red-600 text-xs font-medium">{error}</p>
        </div>
      )}
    </div>
  );
}
