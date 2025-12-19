"use client";


import {useState} from "react";
import AuthShell from "@/components/AuthShell";
import {Card, CardBody} from "@/components/Card";
import {api, setToken} from "@/lib/api";
import {AuthResponse} from "@/lib/types";
import {useRouter} from "next/dist/client/components/navigation";

export default function LoginPage() {
    const router = useRouter();

    const [email, setEmail] = useState("test2@example.com");
    const [password, setPassword] = useState("Password123!");

    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    async function onSubmit(e: React.FormEvent) {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const res = await api<AuthResponse>("/api.auth.login", {
                method: "POST",
                body: JSON.stringify({email, password}),
            });

            setToken(res.access_token);

            router.push("/dashboard");
        } catch (err: unknown) {
            const message = err instanceof Error ? err.message : "Login Failed Brochacho";
            setError(message)
        } finally {
            setLoading(false);
        }
    }
    return (
        <AuthShell>
            <Card>
                <CardBody>
                    <div className="mb-6">
                        <h1 className="text-2x1 font-semibold tracking-tight">Welcome Back monkey</h1>
                        <p className="mt-1 text-sm text-slate-300">
                            Log in to continue tracking workouts and analytics and whatever BLEH
                        </p>
                    </div>

                    {error && (
                        <div className="mb-4 rounded-x1 border border-red-500/30 bg-red-500/110 px-4 py-3 text-sm text-red-200">
                            {error}
                        </div>
                    )}

                    <form onSubmit={onSubmit} className="space-y-4">
                        <div>
                            <label className="text-sm text-slate-200">Email</label>
                            <input
                                className="
                                    mt-1 w-full rounded-x1 border border-white/10 bg-white/5 px-3 py-2.5
                                    text-slate-100 placeholder:text-slate-400
                                    outline-none
                                    focus:border-indigo-400/40 focus:ring-4 focus:ring-indigo-500/20
                                "
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                autoComplete="email"
                                placeholder="you@example.com"
                                required
                            />
                        </div>

                        <div>
                            <label className="text-sm text-slate-200">Password</label>
                            <input
                                className="
                                    mt-1 w-full rounded-xl border border-white/10 bg-white/5 px-3 py-2.5
                                    text-slate-100 placeholder:text-slate-400
                                    outline-none
                                    focus:border-indigo-400/40 focus:ring-4 focus:ring-indigo-500/20
                                "
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                autoComplete="current-password"
                                placeholder="••••••••"
                                required
                            />

                            <button
                                disabled={loading}
                                className="
                                    w-full rounded-xl
                                    bg-indigo-500 px-4 py-2.5
                                    text-sm font-semibold text-white
                                    shadow-lg shadow-indigo-500/20
                                    transition
                                    hover:bg-indigo-400
                                    disabled:opacity-60 disabled:cursor-not-allowed
                                    focus:outline-none focus:ring-4 focus:ring-indigo-500/30
                                "
                            >
                                {loading ? "Logging in brotisserie chicken..." : "Log In HeheheHa"}
                            </button>

                            <div className="pt-2 text-center text-sm text-slate-300">
                                No account?{" "}
                                <a className="font-semibold text-indigo-300 hover:text-indigo-200" href="/register">
                                    Create One
                                </a>
                            </div>
                        </div>
                    </form>
                </CardBody>
            </Card>

            <p className="mt-6 text-center text-xs txt-slate-400">
                AIWorkoutApp Yippeee
            </p>
        </AuthShell>
    )
}