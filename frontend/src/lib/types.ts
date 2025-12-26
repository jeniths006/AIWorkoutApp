export type AuthResponse = {
    accessToken: string;
    token_type: string;
    userId: string;
    email: string;
};

export type MeResponse = {
    userId: string;
    email: string;
}