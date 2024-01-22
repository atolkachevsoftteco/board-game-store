CREATE TYPE game_type AS ENUM(
  'STRATEGY',
  'CLASSIC',
  'CARDS',
  'ECONOMIC');

ALTER TABLE "board_game" ADD COLUMN "g_type" game_type NOT NULL DEFAULT 'CLASSIC';